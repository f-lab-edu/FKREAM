package com.flab.fkream.listing.ItemGenerateStrategy;

import com.flab.fkream.listing.ItemGenerationStrategy;
import com.flab.fkream.listing.ListingCriteria;
import com.flab.fkream.listing.ListingMapper;
import com.flab.fkream.search.dbSearch.SearchItemDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RecommendedItemForMenStrategy implements ItemGenerationStrategy {

    private final ListingCriteria criteria = ListingCriteria.RECOMMENDED_ITEM_FOR_MEN;

    private final ListingMapper listingMapper;

    @Override
    public List<SearchItemDto> generateItems(int month) {
        List<SearchItemDto> searchItemDtos = listingMapper.generateRecommendedItemsForMen(month);
        return searchItemDtos;
    }

    @Override
    public ListingCriteria getCriteria() {
        return criteria;
    }
}
