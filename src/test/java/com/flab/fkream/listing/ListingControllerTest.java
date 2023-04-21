package com.flab.fkream.listing;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.flab.fkream.search.SearchItemDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ListingController.class)
class ListingControllerTest {

    @MockBean
    ListingService listingService;

    @Autowired
    MockMvc mockMvc;

    SearchItemDto searchItemDto = SearchItemDto.builder().build();

    @Test
    void generateRecommendedItemsListForMen() throws Exception {
        given(listingService.generateRecommendedItemsListForMen()).willReturn(
            List.of(searchItemDto));
        mockMvc.perform(get("/listing/for-man")).andExpect(status().isOk());
    }

    @Test
    void generateRecommendedItemsListForWomen() throws Exception {
        given(listingService.generateRecommendedItemsListForWomen()).willReturn(List.of(searchItemDto));
        mockMvc.perform(get("/listing/for-women")).andExpect(status().isOk());
    }

    @Test
    void generateItemsBelowReleasedPrice() throws Exception {
        given(listingService.generateItemsBelowReleasedPrice()).willReturn(List.of(searchItemDto));
        mockMvc.perform(get("/listing/for-item-below-released-price")).andExpect(status().isOk());
    }

    @Test
    void generatePopularLuxuryItems() throws Exception {
        given(listingService.generatePopularLuxuryItems()).willReturn(List.of(searchItemDto));
        mockMvc.perform(get("/listing/popular-luxury")).andExpect(status().isOk());
    }

    @Test
    void generateMostPopularItems() throws Exception {
        given(listingService.generateMostPopularItems()).willReturn(List.of(searchItemDto));
        mockMvc.perform(get("/listing/most-popular")).andExpect(status().isOk());
    }

    @Test
    void generatePopularSneakers() throws Exception {
        given(listingService.generatePopularSneakers()).willReturn(List.of(searchItemDto));
        mockMvc.perform(get("/listing/popular-sneakers")).andExpect(status().isOk());
    }
}