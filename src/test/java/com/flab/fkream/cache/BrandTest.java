package com.flab.fkream.cache;

import static org.mockito.BDDMockito.*;

import com.flab.fkream.brand.Brand;
import com.flab.fkream.brand.BrandMapper;
import com.flab.fkream.brand.BrandService;
import com.flab.fkream.item.Item;
import com.flab.fkream.item.ItemMapper;
import com.flab.fkream.item.ItemService;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class BrandTest {

    @SpyBean
    BrandMapper brandMapper;
    @Autowired
    CacheManager cacheManager;


    @Test
    void brandCacheTest() {
        Brand brand = Brand.builder().id(1L).brandName("구찌").isLuxury(true).build();
        given(brandMapper.findOne(1L)).willReturn(brand);
        brandMapper.findOne(1L);
        brandMapper.findOne(1L);
        Assertions.assertThat(cacheManager.getCache("Brand").get(brand.getId())).isNotNull();
        verify(brandMapper, atMost(1)).findOne(1L);
    }
}
