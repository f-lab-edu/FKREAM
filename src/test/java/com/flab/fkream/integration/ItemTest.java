package com.flab.fkream.integration;

import com.flab.fkream.brand.Brand;
import com.flab.fkream.brand.BrandMapper;
import com.flab.fkream.brand.BrandService;
import com.flab.fkream.item.Item;
import com.flab.fkream.item.ItemService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@SpringBootTest
@Transactional
public class ItemTest {


    @Autowired
    ItemService itemService;
    @Autowired
    BrandService brandService;
    @Autowired
    CacheManager cacheManager;


    @Test
    void 캐시_사용_테스트(){
        Brand brand = Brand.builder().brandName("구찌").isLuxury(true).build();

        Item itemInfo =
            Item.builder()
                .itemName("나이키 에어포스")
                .modelNumber("NK22035")
                .releaseDate(LocalDate.now())
                .representativeColor("Black")
                .releasedPrice(10000)
                .brand(brand)
                .build();

        brandService.addBrand(brand);
        itemService.addItem(itemInfo);

        itemService.findOne(itemInfo.getId());
        Assertions.assertThat(cacheManager.getCache("Brand").get(brand.getId())).isNotNull();
    }
}
