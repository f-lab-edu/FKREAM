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

    public List<SearchItemDto> generateItems(ListingCriteria listingCriteria) {
        final int month = 1;
        if (listingCriteria == ListingCriteria.RECOMMENDED_ITEM_FOR_MEN) {
            return listingMapper.generateRecommendedItemsForMen(month);
        }
        if (listingCriteria == ListingCriteria.RECOMMENDED_ITEM_FOR_WOMEN) {
            return listingMapper.generateRecommendedItemsForWomen(month);
        }
        if (listingCriteria == ListingCriteria.ITEMS_BELOW_RELEASED_PRICE) {
            return listingMapper.generateItemsBelowReleasedPrice(month);
        }
        if (listingCriteria == ListingCriteria.POPULAR_LUXURY_ITEMS) {
            return listingMapper.generatePopularLuxuryItems(month);
        }
        if (listingCriteria == ListingCriteria.MOST_POPULAR) {
            return listingMapper.generateMostPopularItems();
        }
        if (listingCriteria == ListingCriteria.POPULAR_SNEAKERS) {
            return listingMapper.generatePopularSneakers(month);
        }
        throw new IllegalArgumentException("리스팅 기준이 잘못 입력되었습니다.");
    }
}
