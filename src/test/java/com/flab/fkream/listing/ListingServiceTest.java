package com.flab.fkream.listing;

import static org.mockito.BDDMockito.*;

import com.flab.fkream.search.SearchItemDto;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ListingServiceTest {

    @Mock
    ListingMapper listingMapper;

    @InjectMocks
    ListingService listingService;

    SearchItemDto searchItemDto = SearchItemDto.builder().build();

    @Test
    void generateRecommendedItemsListForMen() {
        given(listingMapper.generateRecommendedItemsForMen(1)).willReturn(
            List.of(searchItemDto));
        listingService.generateItems(ListingCriteria.RECOMMENDED_ITEM_FOR_MEN);
        then(listingMapper).should().generateRecommendedItemsForMen(1);
    }

    @Test
    void generateRecommendedItemsListForWomen() {
        given(listingMapper.generateRecommendedItemsForWomen(1)).willReturn(
            List.of(searchItemDto));
        listingService.generateItems(ListingCriteria.RECOMMENDED_ITEM_FOR_WOMEN);
        then(listingMapper).should().generateRecommendedItemsForWomen(1);
    }

    @Test
    void generateItemsBelowReleasedPrice() {
        given(listingMapper.generateItemsBelowReleasedPrice(1)).willReturn(
            List.of(searchItemDto));
        listingService.generateItems(ListingCriteria.ITEMS_BELOW_RELEASED_PRICE);
        then(listingMapper).should().generateItemsBelowReleasedPrice(1);
    }

    @Test
    void generatePopularLuxuryItems() {
        given(listingMapper.generatePopularLuxuryItems(1)).willReturn(
            List.of(searchItemDto));
        listingService.generateItems(ListingCriteria.POPULAR_LUXURY_ITEMS);
        then(listingMapper).should().generatePopularLuxuryItems(1);
    }

    @Test
    void generateMostPopularItems() {
        given(listingMapper.generateMostPopularItems()).willReturn(
            List.of(searchItemDto));
        listingService.generateItems(ListingCriteria.MOST_POPULAR);
        then(listingMapper).should().generateMostPopularItems();
    }

    @Test
    void generatePopularSneakers() {
        given(listingMapper.generatePopularSneakers(1)).willReturn(
            List.of(searchItemDto));
        listingService.generateItems(ListingCriteria.POPULAR_SNEAKERS);
        then(listingMapper).should().generatePopularSneakers(1);
    }
}