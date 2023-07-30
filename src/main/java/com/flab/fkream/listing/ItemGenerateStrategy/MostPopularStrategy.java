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
public class MostPopularStrategy implements ItemGenerationStrategy {

    private final ListingCriteria criteria = ListingCriteria.MOST_POPULAR;
    private final ListingMapper listingMapper;

    @Override
    public List<SearchItemDto> generateItems(int month) {
        return listingMapper.generateMostPopularItems();
    }

    @Override
    public ListingCriteria getCriteria() {
        return criteria;
    }


}
