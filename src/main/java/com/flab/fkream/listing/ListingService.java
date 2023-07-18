package com.flab.fkream.listing;

import com.flab.fkream.search.dbSearch.SearchItemDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ListingService {

    private final ItemGenerationStrategyFactory itemGenerationStrategyFactory;

    public List<SearchItemDto> generateItems(ListingCriteria listingCriteria) {
        final int month = 1;
        ItemGenerationStrategy strategy = itemGenerationStrategyFactory.get(listingCriteria);
        if (strategy == null) {
            throw new IllegalArgumentException("리스팅 기준이 잘못 입력되었습니다.");
        }
        return strategy.generateItems(month);
    }

}
