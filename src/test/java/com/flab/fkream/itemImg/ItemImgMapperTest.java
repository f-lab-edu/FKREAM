package com.flab.fkream.itemImg;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.fkream.brand.Brand;
import com.flab.fkream.item.Item;
import com.flab.fkream.mapper.ItemMapper;
import com.flab.fkream.mapper.ItemImgMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles({"test"})
class ItemImgMapperTest {

    @Autowired
    ItemImgMapper itemImgMapper;
    @Autowired
    ItemMapper itemMapper;

    Item itemInfo =
        Item.builder()
            .itemName("나이키 에어포스")
            .modelNumber("NK22035")
            .categoryId(1L)
            .detailedCategoryId(2L)
            .releaseDate(LocalDate.now())
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
            //.isRepresentativeImg(true)
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
