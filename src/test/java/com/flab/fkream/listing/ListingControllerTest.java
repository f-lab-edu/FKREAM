package com.flab.fkream.listing;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.flab.fkream.search.dbSearch.SearchItemDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ListingController.class)
@ActiveProfiles({"test"})
class ListingControllerTest {

    @MockBean
    ListingService listingService;

    @Autowired
    MockMvc mockMvc;

    SearchItemDto searchItemDto = SearchItemDto.builder().build();

    @Test
    void generateRecommendedItemsListForMen() throws Exception {
        given(listingService.generateItems(ListingCriteria.RECOMMENDED_ITEM_FOR_MEN)).willReturn(
            List.of(searchItemDto));
        mockMvc.perform(get("/listing")).andExpect(status().isOk());
    }

    @Test
    void generateRecommendedItemsListForWomen() throws Exception {
        given(listingService.generateItems(ListingCriteria.RECOMMENDED_ITEM_FOR_WOMEN)).willReturn(List.of(searchItemDto));
        mockMvc.perform(get("/listing")).andExpect(status().isOk());
    }

    @Test
    void generateItemsBelowReleasedPrice() throws Exception {
        given(listingService.generateItems(ListingCriteria.ITEMS_BELOW_RELEASED_PRICE)).willReturn(List.of(searchItemDto));
        mockMvc.perform(get("/listing")).andExpect(status().isOk());
    }

    @Test
    void generatePopularLuxuryItems() throws Exception {
        given(listingService.generateItems(ListingCriteria.POPULAR_LUXURY_ITEMS)).willReturn(List.of(searchItemDto));
        mockMvc.perform(get("/listing")).andExpect(status().isOk());
    }

    @Test
    void generateMostPopularItems() throws Exception {
        given(listingService.generateItems(ListingCriteria.MOST_POPULAR)).willReturn(List.of(searchItemDto));
        mockMvc.perform(get("/listing")).andExpect(status().isOk());
    }

    @Test
    void generatePopularSneakers() throws Exception {
        given(listingService.generateItems(ListingCriteria.POPULAR_SNEAKERS)).willReturn(List.of(searchItemDto));
        mockMvc.perform(get("/listing")).andExpect(status().isOk());
    }
}