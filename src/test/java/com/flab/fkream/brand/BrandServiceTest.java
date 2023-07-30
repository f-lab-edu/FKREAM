package com.flab.fkream.brand;

import com.flab.fkream.mapper.BrandMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @Mock
    BrandMapper brandMapper;
    @InjectMocks
    BrandService brandService;

    Brand brandInfo = Brand.builder().brandName("샤넬").luxury(true).build();

    @Test
    void 브랜드_추가() throws Exception {

        given(brandMapper.save(brandInfo)).willReturn(1);
        brandService.addBrand(brandInfo);
        then(brandMapper).should().save(brandInfo);
    }

    @Test
    void 브랜드_조회() throws Exception {
        given(brandMapper.findOne(1L)).willReturn(brandInfo);
        brandService.findOne(1L);
        then(brandMapper).should().findOne(1L);
    }

    @Test
    void 브랜드_리스팅() {
        given(brandMapper.findAll()).willReturn(List.of(brandInfo));
        brandService.findAll();
        then(brandMapper).should().findAll();
    }

    @Test
    void 브랜드_업데이트() {
        given(brandMapper.update(brandInfo)).willReturn(1);
        brandService.update(brandInfo);
        then(brandMapper).should().update(brandInfo);
    }

    @Test
    void 브랜드_삭제() {
        given(brandMapper.delete(1L)).willReturn(1);
        brandService.delete(1L);
        then(brandMapper).should().delete(1L);
    }
}
