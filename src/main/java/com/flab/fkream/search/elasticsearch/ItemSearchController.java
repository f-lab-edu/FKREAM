package com.flab.fkream.search.elasticsearch;

import com.flab.fkream.kafka.KafkaMessageSender;
import com.flab.fkream.kafka.KafkaTopic;
import com.flab.fkream.search.SearchCriteria;
import com.flab.fkream.utils.HttpRequestUtils;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class ItemSearchController {

    private final ItemSearchService itemSearchService;
    private final SearchRankingService searchRankingService;
    private final RedissonClient redissonClient;
    private final KafkaMessageSender messageSender;


    @GetMapping("/search_rankings")
    public List<SearchDocument> getSearchRanking() {
        return searchRankingService.getSearchRanking();
    }

    @PostMapping("/item_search")
    public ItemDocumentsDto findBySearchCriteria(@RequestBody SearchCriteria searchCriteria)
        throws IOException {
        RMap<String, LocalDateTime> ipHistory = redissonClient.getMap("ipHistory");

        String ipAddress = HttpRequestUtils.getIpAddress();
        String context = searchCriteria.getContext();
        String contextWithoutWhitespace = context.replaceAll("\\s", "");
        String key = ipAddress + "-" + contextWithoutWhitespace;

        if (!ipHistory.containsKey(key) || LocalDateTime.now().isAfter(ipHistory.get(key))) {
            messageSender.send(KafkaTopic.SEARCH_LOG, searchCriteria);
            ipHistory.put(key, LocalDateTime.now().plusMinutes(10));
        }

        return itemSearchService.search(searchCriteria);
    }

    /*
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
    */
}
