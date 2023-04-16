package com.flab.fkream.search;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(SearchController.class)
class SearchControllerTest {

    @MockBean
    SearchService searchService;

    @Autowired
    MockMvc mockMvc;

    private static final String CONTEXT = "nike";

    SearchItemDto searchItemDto = SearchItemDto.builder()
        .itemId(1L)
        .itemName("나이키 에어포스")
        .brandId(2L)
        .brandName("nike")
        .buyNowLowestPrice(1000)
        .itemImgId(2L)
        .imgName("test1234")
        .imgUrl("test")
        .build();

    AutoCompletedItemDto autoCompletedItemDto = AutoCompletedItemDto.builder()
        .itemId(1L)
        .itemName("나이키 에어포스")
        .itemImgId(2L)
        .imgName("test1234")
        .imgUrl("test")
        .build();

    @Test
    void searchItem() throws Exception {
        given(searchService.search(CONTEXT)).willReturn(List.of(searchItemDto));
        mockMvc.perform(get("/search?context=nike")).andExpect(status().isOk())
            .andExpect(content().contentType(
                MediaType.APPLICATION_JSON));
    }

    @Test
    void searchItemCount() throws Exception {
        given(searchService.findCount(CONTEXT)).willReturn(20);
        mockMvc.perform(get("/search/count?context=nike")).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void searchAutoCompletedItem() throws Exception {
        given(searchService.autoComplete(CONTEXT)).willReturn(List.of(autoCompletedItemDto));
        mockMvc.perform(get("/search/count?context=nike")).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}