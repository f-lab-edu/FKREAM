package com.flab.fkream.elasticsearch;

import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ItemSearchRepository extends ElasticsearchRepository<ItemDocument, Long> {

    List<ItemDocument> findByItemName(String itemName);
}
