package com.flab.fkream.itemImg;

import com.flab.fkream.brand.Brand;
import com.flab.fkream.item.Item;
import com.flab.fkream.item.ItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ItemImgMapperTest {

    @Autowired
    ItemImgMapper itemImgMapper;
    @Autowired
    ItemMapper itemMapper;

    Item itemInfo =
        Item.builder()
            .itemName("나이키 에어포스")
            .modelNumber("NK22035")
            .category1("신발")
            .category2("스니커즈")
            .releaseDate(LocalDateTime.now())
            .representativeColor("Black")
            .releasedPrice(10000)
            .brand(new Brand())
            .build();

    ItemImg itemImgInfo =
        ItemImg.builder()
            .item(itemInfo)
            .imgUrl("test")
            .imgName("test")
            .originName("test_origin")
            .isRepresentativeImg(true)
            .build();

    @BeforeEach
    void setUp() {
        itemMapper.save(itemInfo);
    }

    @Test
    void save() {
        assertThat(itemImgMapper.save(itemImgInfo)).isEqualTo(1);
    }

    @Test
    void findImagesByItemId() {
        itemImgMapper.save(itemImgInfo);
        assertThat(itemImgMapper.findImagesByItemId(itemInfo.getId()).get(0)).isEqualTo(
            itemImgInfo);
    }

    @Test
    void delete() {
        itemImgMapper.save(itemImgInfo);
        itemImgMapper.delete(itemImgInfo.getId());
        assertThat(itemImgMapper.findImagesByItemId(itemInfo.getId())).hasSize(0);
    }
}
