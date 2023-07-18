package com.flab.fkream.search.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ItemSearchRepository extends ElasticsearchRepository<ItemDocument, Long> {

}
