package com.flab.fkream.elasticsearch;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchRankingService {

    private final SearchRankingRepository searchRankingRepository;

    public void saveSearchDocument(SearchDocument searchDocument) {
        searchRankingRepository.save(searchDocument);
    }


    @Transactional
    public void saveAllSearchDocuments(List<SearchDocument> searchDocumentList) {
        searchRankingRepository.saveAll(searchDocumentList);
    }

    public List<SearchDocument> getSearchRanking() {
        LocalDateTime now = LocalDateTime.now();
        if (now.getMinute() >= 5) {
            return searchRankingRepository.findTop10ByCreatedAtOrderBySearchCountDesc(
                now.truncatedTo(
                    ChronoUnit.HOURS));
        } else {
            return searchRankingRepository.findTop10ByCreatedAtOrderBySearchCountDesc(
                now.minusHours(1).truncatedTo(ChronoUnit.HOURS));
        }
    }

}

