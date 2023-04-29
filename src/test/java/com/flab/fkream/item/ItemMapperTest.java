package com.flab.fkream.item;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.fkream.brand.Brand;
import com.flab.fkream.brand.BrandMapper;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ItemMapperTest {

    @Autowired
    ItemMapper itemMapper;
    @Autowired
    BrandMapper brandMapper;

    Brand brandInfo = Brand.builder().brandName("구찌").isLuxury(true).build();

    Item itemInfo =
        Item.builder()
            .itemName("나이키 에어포스")
            .modelNumber("NK22035")
            .categoryId(1L)
            .categoryId(2L)
            .releaseDate(LocalDate.now())
            .representativeColor("Black")
            .releasedPrice(10000)
            .brand(brandInfo)
            .build();

    @BeforeEach
    void setUp() {
        brandMapper.save(brandInfo);
    }

    @Test
    void save() {
        assertThat(itemMapper.save(itemInfo)).isEqualTo(1);
    }

    @Test
    void findOne() {
        itemMapper.save(itemInfo);
        assertThat(itemMapper.findOne(itemInfo.getId())).isEqualTo(itemInfo);
    }

    @Test
    void findAll() {
        List<Item> all = itemMapper.findAll();
        int DATA_COUNT = 100;
        assertThat(all.size()).isEqualTo(DATA_COUNT);
    }

    @Test
    void update() {
        itemMapper.save(itemInfo);
        Long id = itemInfo.getId();
        Item itemUpdated =
            Item.builder()
                .id(id)
                .itemName("나이키 에어포스")
                .modelNumber("NK22035")
                .categoryId(1L)
                .detailedCategoryId(2L)
                .releaseDate(LocalDate.now())
                .representativeColor("Black")
                .releasedPrice(10)
                .brand(brandInfo)
                .build();
        itemMapper.update(itemUpdated);
        assertThat(itemMapper.findOne(itemInfo.getId())).isEqualTo(itemUpdated);
    }

    @Test
    void delete() {
        itemMapper.save(itemInfo);
        itemMapper.delete(itemInfo.getId());
        assertThat(itemMapper.findOne(itemInfo.getId())).isNull();
    }
}
