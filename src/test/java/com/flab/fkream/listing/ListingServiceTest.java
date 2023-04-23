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
        given(listingMapper.generateRecommendedItemsForMen(LocalDate.now(),
            LocalDate.now().minusMonths(1))).willReturn(
            List.of(searchItemDto));
        listingService.generateRecommendedItemsListForMen();
        then(listingMapper).should().generateRecommendedItemsForMen(LocalDate.now(),
            LocalDate.now().minusMonths(1));
    }

    @Test
    void generateRecommendedItemsListForWomen() {
        given(listingMapper.generateRecommendedItemsForWomen(LocalDate.now(),
            LocalDate.now().minusMonths(1))).willReturn(
            List.of(searchItemDto));
        listingService.generateRecommendedItemsListForWomen();
        then(listingMapper).should().generateRecommendedItemsForWomen(LocalDate.now(),
            LocalDate.now().minusMonths(1));
    }

    @Test
    void generateItemsBelowReleasedPrice() {
        given(listingMapper.generateItemsBelowReleasedPrice(LocalDate.now(),
            LocalDate.now().minusMonths(1))).willReturn(
            List.of(searchItemDto));
        listingService.generateItemsBelowReleasedPrice();
        then(listingMapper).should().generateItemsBelowReleasedPrice(LocalDate.now(),
            LocalDate.now().minusMonths(1));
    }

    @Test
    void generatePopularLuxuryItems() {
        given(listingMapper.generatePopularLuxuryItems(LocalDate.now(),
            LocalDate.now().minusMonths(1))).willReturn(
            List.of(searchItemDto));
        listingService.generatePopularLuxuryItems();
        then(listingMapper).should().generatePopularLuxuryItems(LocalDate.now(),
            LocalDate.now().minusMonths(1));
    }

    @Test
    void generateMostPopularItems() {
        given(listingMapper.generateMostPopularItems()).willReturn(
            List.of(searchItemDto));
        listingService.generateMostPopularItems();
        then(listingMapper).should().generateMostPopularItems();
    }

    @Test
    void generatePopularSneakers() {
        given(listingMapper.generatePopularSneakers(LocalDate.now(),
            LocalDate.now().minusMonths(1), LocalDate.now().minusMonths(2))).willReturn(
            List.of(searchItemDto));
        listingService.generatePopularSneakers();
        then(listingMapper).should().generatePopularSneakers(LocalDate.now(),
            LocalDate.now().minusMonths(1), LocalDate.now().minusMonths(2));
    }
}