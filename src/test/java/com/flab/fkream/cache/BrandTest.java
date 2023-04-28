package com.flab.fkream.cache;

import com.flab.fkream.brand.Brand;
import com.flab.fkream.brand.BrandService;
import com.flab.fkream.item.Item;
import com.flab.fkream.item.ItemService;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class BrandTest {


    @Autowired
    ItemService itemService;
    @Autowired
    BrandService brandService;
    @Autowired
    CacheManager cacheManager;


    @Test
    void 브랜드_캐시_사용_테스트() {
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
