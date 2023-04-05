package com.flab.fkream.item;

import com.flab.fkream.brand.Brand;
import com.flab.fkream.brand.BrandMapper;
import com.flab.fkream.brand.BrandService;
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
    @Mock
    BrandService brandService;
    @InjectMocks
    ItemService itemService;

    Brand brand = Brand.builder().brandName("구찌").isLuxury(true).build();

    Item itemInfo =
        Item.builder()
            .itemName("나이키 에어포스")
            .modelNumber("NK22035")
            .category1("신발")
            .category2("스니커즈")
            .releaseDate(LocalDateTime.now())
            .representativeColor("Black")
            .releasedPrice(10000)
            .brand(brand)
            .build();

    @Test
    void 아이템_생성() {
        given(itemMapper.save(itemInfo)).willReturn(1);
        itemService.addItem(itemInfo);
        then(itemMapper).should().save(itemInfo);
    }

    @Test
    void 아이템_조회() {
        given(itemMapper.findOne(1L)).willReturn(itemInfo);
        given(brandService.findOne(brand.getId())).willReturn(brand);
        itemService.findOne(1L);
        then(itemMapper).should().findOne(1L);
    }

    @Test
    void 아이템_리스팅() {
        given(itemMapper.findAll()).willReturn(List.of(itemInfo));
        given(brandService.findOne(brand.getId())).willReturn(brand);
        itemService.findAll();
        then(itemMapper).should().findAll();
    }

    @Test
    void 아이템_업데이트() {
        given(itemMapper.update(itemInfo)).willReturn(1);
        itemService.update(itemInfo);
        then(itemMapper).should().update(itemInfo);
    }

    @Test
    void 아이템_삭제() {
        given(itemMapper.delete(1L)).willReturn(1);
        itemService.delete(1L);
        then(itemMapper).should().delete(1L);
    }
}
