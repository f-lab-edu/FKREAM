package com.flab.fkream.listing;

import com.flab.fkream.search.dbSearch.SearchItemDto;
import java.util.List;

public interface ItemGenerationStrategy {
    List<SearchItemDto> generateItems(int month);

    ListingCriteria getCriteria();
}
