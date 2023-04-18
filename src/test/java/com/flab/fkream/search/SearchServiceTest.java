package com.flab.fkream.search;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.flab.fkream.itemCategory.ItemCategory;
import com.flab.fkream.itemCategory.ItemCategoryService;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    SearchMapper searchMapper;
    @Mock
    Trie trie;

    @Mock
    ItemCategoryService itemCategoryService;
    @InjectMocks
    SearchService searchService;

    private static final String CONTEXT = "nike";

    private static final Long[] CATEGORY_ID = {1L, 2L};

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

    SearchCriteria searchCriteria = SearchCriteria.builder().context("nike").build();

    @Test
    void searchAll() {
        given(searchMapper.searchAll()).willReturn(List.of(searchItemDto));
        assertThat(searchService.search(null)).contains(searchItemDto);
        then(searchMapper).should().searchAll();
    }

    @Test
    void searchByCriteria() {
        given(searchMapper.searchByCriteria(searchCriteria)).willReturn(
            List.of(searchItemDto));
        given(itemCategoryService.isValidCategoryId(searchCriteria.getCategoryId())).willReturn(true);
        assertThat(searchService.search(searchCriteria)).contains(searchItemDto);
        then(searchMapper).should().searchByCriteria(searchCriteria);
    }

    @Test
    void findCount() {
        given(searchMapper.findAllCount()).willReturn(200);
        assertThat(searchService.findCount(null)).isEqualTo(200);
        then(searchMapper).should().findAllCount();
    }

    @Test
    void findCountByCriteria() {
        given(searchMapper.findCountByCriteria(searchCriteria)).willReturn(200);
        given(itemCategoryService.isValidCategoryId(searchCriteria.getCategoryId())).willReturn(true);
        assertThat(searchService.findCount(searchCriteria)).isEqualTo(200);
        then(searchMapper).should().findCountByCriteria(searchCriteria);
    }



    @Test
    void autoComplete() {
        List<String> result = List.of("nike");
        given(trie.search(CONTEXT)).willReturn(result);
        given(searchMapper.autoComplete(result)).willReturn(List.of(autoCompletedItemDto));
        assertThat(searchService.autoComplete(CONTEXT)).contains(autoCompletedItemDto);
        then(trie).should().search(CONTEXT);
        then(searchMapper).should().autoComplete(result);
    }
}