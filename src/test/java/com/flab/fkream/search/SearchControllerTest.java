package com.flab.fkream.search;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.flab.fkream.item.ItemGender;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@WebMvcTest(SearchController.class)
@ActiveProfiles({"test"})
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
        .price(1000)
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

    SearchCriteria criteria = SearchCriteria.builder().context("nike").gender(ItemGender.MALE)
        .build();

    @Test
    void searchAll() throws Exception {
        given(searchService.search(null)).willReturn(List.of(searchItemDto));
        mockMvc.perform(get("/search")).andExpect(status().isOk())
            .andExpect(content().contentType(
                MediaType.APPLICATION_JSON));
    }


    @Test
    void searchItemByCriteria() throws Exception {
        given(searchService.search(criteria)).willReturn(List.of(searchItemDto));
        mockMvc.perform(get("/search?context=nike&gender=male")
            ).andExpect(status().isOk())
            .andExpect(content().contentType(
                MediaType.APPLICATION_JSON));
    }

    @Test
    void searchAllItemCount() throws Exception {
        given(searchService.findCount(null)).willReturn(20);
        mockMvc.perform(get("/search/count")).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void searchItemCountByCriteria() throws Exception {
        given(searchService.findCount(criteria)).willReturn(20);
        mockMvc.perform(get("/search/count?context=nike&gender=male")).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void searchAutoCompletedItem() throws Exception {
        given(searchService.autoComplete(CONTEXT)).willReturn(List.of(autoCompletedItemDto));
        mockMvc.perform(get("/search/count?context=nike")).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}