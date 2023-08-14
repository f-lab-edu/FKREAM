package com.flab.fkream.search.elasticsearch;

import com.flab.fkream.item.Item;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ItemSearchRepository extends ElasticsearchRepository<ItemDocument, Long> {

    List<ItemDocument> findTop5ByItemNameStartingWithOrderByDealCount(String itemName);
}
