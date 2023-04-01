package com.flab.fkream.brand;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @Mock
    BrandMapper brandMapper;
    @InjectMocks
    BrandService brandService;

    Brand brandInfo = Brand.builder().brandName("샤넬").isLuxury(true).build();

    @Test
    void 브랜드_추가() throws Exception {

        given(brandMapper.save(brandInfo)).willReturn(1);
        brandService.addBrand(brandInfo);
    }

    @Test
    void 브랜드_조회() throws Exception {
        given(brandMapper.findOne(1L)).willReturn(brandInfo);
        brandService.findOne(1L);
    }

    @Test
    void 브랜드_리스팅() {
        given(brandMapper.findAll()).willReturn(List.of());
        brandService.findAll();
    }

    @Test
    void 브랜드_업데이트() {
        given(brandMapper.update(brandInfo)).willReturn(1);
        brandService.update(brandInfo);
    }

    @Test
    void 브랜드_삭제() {
        given(brandMapper.delete(1L)).willReturn(1);
        brandService.delete(1L);
    }
}
