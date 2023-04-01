package com.flab.fkream.item;

import com.flab.fkream.brand.Brand;
import com.flab.fkream.brand.BrandMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

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
            .category1("신발")
            .category2("스니커즈")
            .releaseDate(LocalDateTime.now())
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
        itemMapper.save(itemInfo);
        itemMapper.save(itemInfo);
        List<Item> all = itemMapper.findAll();
        assertThat(all.size()).isEqualTo(2);
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
                .category1("신발")
                .category2("스니커즈")
                .releaseDate(LocalDateTime.now())
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
