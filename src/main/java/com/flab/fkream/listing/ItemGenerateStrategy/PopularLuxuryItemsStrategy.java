package com.flab.fkream.listing.ItemGenerateStrategy;

import com.flab.fkream.listing.ItemGenerationStrategy;
import com.flab.fkream.listing.ListingCriteria;
import com.flab.fkream.mapper.ListingMapper;
import com.flab.fkream.search.dbSearch.SearchItemDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PopularLuxuryItemsStrategy implements ItemGenerationStrategy {

    private final ListingCriteria criteria =  ListingCriteria.POPULAR_LUXURY_ITEMS;

    private final ListingMapper listingMapper;

    @Override
    public List<SearchItemDto> generateItems(int month) {
        List<SearchItemDto> searchItemDtos = listingMapper.generatePopularLuxuryItems(month);
        return searchItemDtos;
    }

    @Override
    public ListingCriteria getCriteria() {
        return criteria;
    }

}
