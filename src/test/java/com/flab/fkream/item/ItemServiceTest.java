package com.flab.fkream.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {


    @Mock
    ItemMapper itemMapper;
    @InjectMocks
    ItemService itemService;

    Item itemInfo = Item.builder()
            .itemName("나이키 에어포스")
            .modelNumber("NK22035")
            .category1("신발")
            .category2("스니커즈")
            .releaseDate(LocalDateTime.now())
            .representativeColor("Black")
            .releasedPrice(10000)
            .build();

    @Test
    void 아이템_생성() {
        given(itemMapper.save(itemInfo)).willReturn(1);
        itemService.addItem(itemInfo);
    }

    @Test
    void 아이템_조회() {
        given(itemMapper.findOne(1L)).willReturn(itemInfo);
        itemService.findOne(1L);
    }

    @Test
    void 아이템_리스팅() {
        given(itemMapper.findAll()).willReturn(List.of());
        itemService.findAll();
    }

    @Test
    void 아이템_업데이트() {
        given(itemMapper.update(itemInfo)).willReturn(1);
        itemService.update(itemInfo);
    }

    @Test
    void 아이템_삭제() {
        given(itemMapper.delete(1L)).willReturn(1);
        itemService.delete(1L);
    }
}