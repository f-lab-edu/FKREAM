package com.flab.fkream.search;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    SearchMapper searchMapper;

    @InjectMocks
    SearchService searchService;

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


    @Test
    void search() {
        given(searchMapper.search(CONTEXT)).willReturn(List.of(searchItemDto));
        assertThat(searchService.search(CONTEXT)).contains(searchItemDto);
        then(searchMapper).should().search(CONTEXT);
    }

    @Test
    void findCount() {
        given(searchMapper.findCount(CONTEXT)).willReturn(200);
        assertThat(searchService.findCount(CONTEXT)).isEqualTo(200);
        then(searchMapper).should().findCount(CONTEXT);
    }
}