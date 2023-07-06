package com.flab.fkream.elasticsearch;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchRankingRepository extends ElasticsearchRepository<SearchDocument, Long> {

    List<SearchDocument> findTop10ByCreatedAtOrderBySearchCountDesc(LocalDateTime createdAt);
}
