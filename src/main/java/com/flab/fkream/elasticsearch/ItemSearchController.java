package com.flab.fkream.elasticsearch;

import com.flab.fkream.kafka.KafkaMessageSender;
import com.flab.fkream.kafka.KafkaTopic;
import com.flab.fkream.utils.HttpRequestUtils;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class ItemSearchController {

    private final ItemSearchService itemSearchService;
    private final SearchRankingService searchRankingService;
    private final RedissonClient redissonClient;
    private final KafkaMessageSender messageSender;


    @PostMapping("/search_rankings")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAllSearchRanking(@RequestBody List<SearchDocument> searchDocumentList) {
        searchRankingService.saveAllSearchDocuments(searchDocumentList);
    }

    @PostMapping("/search_ranking")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAllSearchRanking1(@RequestBody SearchDocument searchDocument) {
        searchRankingService.saveSearchDocument(searchDocument);
    }

    @GetMapping("/search_rankings")
    @ResponseStatus(HttpStatus.OK)
    public List<SearchDocument> getSearchRanking() {
        return searchRankingService.getSearchRanking();
    }

    @PostMapping("/item_document")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveItem(@RequestBody ItemDocument itemDocument) {
        itemSearchService.saveItem(itemDocument);
    }

    @PostMapping("/item_documents")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAllItems(@RequestBody List<ItemDocument> itemDocumentList) {
        itemSearchService.saveAllItem(itemDocumentList);
    }

    @PostMapping("/item_search")
    @ResponseStatus(HttpStatus.OK)
    public ItemDocumentsDto findBySearchCriteria(@RequestBody ElasticSearchCriteria searchCriteria)
        throws IOException {
        // redis Map
        RMap<String, LocalDateTime> ipHistory = redissonClient.getMap("ipHistory");

        String ipAddress = HttpRequestUtils.getIpAddress();
        String context = searchCriteria.getContext();
        String contextWithoutWhitespace = context.replaceAll("\\s", "");
        String key = ipAddress + "-" + contextWithoutWhitespace;

        if (!ipHistory.containsKey(key)) {
            log.info("키가 맵에 없음");
        }
        if (ipHistory.get(key) != null && LocalDateTime.now().isAfter(ipHistory.get(key))) {
            log.info("10분 지났음");
        } else {
            log.info("10분 안지났음");
        }
        // 해당 IP로 검색된 적이 없거나, 10분 내에 같은 IP로 동일한 검색을 하지 않은 경우
        if (!ipHistory.containsKey(key) || LocalDateTime.now().isAfter(ipHistory.get(key))) {
            messageSender.send(KafkaTopic.SEARCH_LOG, searchCriteria);
            ipHistory.put(key, LocalDateTime.now().plusMinutes(10));
        }

        return itemSearchService.findBySearchCriteria(searchCriteria);
    }
}
