package com.flab.fkream.listing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ItemGenerationStrategyFactory {

    private Map<ListingCriteria, ItemGenerationStrategy> strategies;

    public ItemGenerationStrategyFactory(List<ItemGenerationStrategy> itemGenerationStrategies) {
        this.strategies = new HashMap<>();
        for (ItemGenerationStrategy itemGenerationStrategy : itemGenerationStrategies) {
            strategies.put(itemGenerationStrategy.getCriteria(), itemGenerationStrategy);
        }
    }

    public ItemGenerationStrategy get(ListingCriteria listingCriteria) {
        return strategies.get(listingCriteria);
    }
}
