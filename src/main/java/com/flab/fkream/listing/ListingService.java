package com.flab.fkream.listing;

import com.flab.fkream.search.SearchItemDto;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ListingService {

    private final ListingMapper listingMapper;

    public List<SearchItemDto> generateRecommendedItemsListForMen() {
        LocalDate now = LocalDate.now();
        LocalDate lastMonth = now.minusMonths(1);
        return listingMapper.generateRecommendedItemsListForMen(now, lastMonth);
    }

    public List<SearchItemDto> generateRecommendedItemsListForWomen() {
        LocalDate now = LocalDate.now();
        LocalDate lastMonth = now.minusMonths(1);
        return listingMapper.generateRecommendedItemsListForWomen(now, lastMonth);
    }

    public List<SearchItemDto> generateItemsBelowReleasedPrice() {
        LocalDate now = LocalDate.now();
        LocalDate lastMonth = now.minusMonths(1);
        return listingMapper.generateItemsBelowReleasedPrice(now, lastMonth);
    }

    public List<SearchItemDto> generatePopularLuxuryItems() {
        LocalDate now = LocalDate.now();
        LocalDate lastMonth = now.minusMonths(1);
        return listingMapper.generatePopularLuxuryItems(now, lastMonth);

    }

    public List<SearchItemDto> generateMostPopularItems() {
        return listingMapper.generateMostPopularItems();

    }

    public List<SearchItemDto> generatePopularSneakers() {
        LocalDate now = LocalDate.now();
        LocalDate lastMonth = now.minusMonths(1);
        LocalDate twoMonthAgo = lastMonth.minusMonths(1);
        return listingMapper.generatePopularSneakers(now, lastMonth, twoMonthAgo);
    }
}
