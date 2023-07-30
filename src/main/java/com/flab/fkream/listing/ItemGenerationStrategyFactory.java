package com.flab.fkream.listing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemGenerationStrategyFactory {

    private final Map<ListingCriteria, ItemGenerationStrategy> strategies;

    @Autowired
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
