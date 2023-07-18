package com.flab.fkream.listing.ItemGenerateStrategy;

import com.flab.fkream.listing.ItemGenerationStrategy;
import com.flab.fkream.listing.ListingCriteria;
import com.flab.fkream.listing.ListingMapper;
import com.flab.fkream.search.dbSearch.SearchItemDto;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemsBelowReleasedPriceStrategy implements ItemGenerationStrategy {

    private final ListingCriteria criteria =  ListingCriteria.ITEMS_BELOW_RELEASED_PRICE;

    private final ListingMapper listingMapper;

    @Override
    public List<SearchItemDto> generateItems(int month) {
        List<SearchItemDto> searchItemDtos = listingMapper.generateItemsBelowReleasedPrice(month);
        return searchItemDtos;
    }

    @Override
    public ListingCriteria getCriteria() {
        return criteria;
    }
}
