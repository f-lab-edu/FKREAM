package com.flab.fkream.itemSizePrice;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.fkream.brand.Brand;
import com.flab.fkream.item.Item;
import com.flab.fkream.mapper.ItemMapper;
import com.flab.fkream.mapper.ItemSizePriceMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;


@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles({"test"})
class ItemSizePriceMapperTest {


    @Autowired
    ItemSizePriceMapper itemSizePriceMapper;

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
    private static final String SIZE = "260";

    ItemSizePrice itemSizePriceInfo;


    @BeforeEach
    void setUp() {
        itemMapper.save(itemInfo);
        itemSizePriceInfo = ItemSizePrice.builder()
            .itemId(itemInfo.getId())
            .size(SIZE)
            .build();
    }

    @Test
    void save() {
        assertThat(itemSizePriceMapper.save(itemSizePriceInfo)).isEqualTo(1);
    }

    @Test
    void findOne() {
        itemSizePriceMapper.save(itemSizePriceInfo);
        assertThat(itemSizePriceMapper.findOne(itemSizePriceInfo.getId())).isEqualTo(
            itemSizePriceInfo);
    }

    @Test
    void findAllByItemId() {
        itemSizePriceMapper.save(itemSizePriceInfo);
        assertThat(itemSizePriceMapper.findAllByItemId(itemInfo.getId())).hasSize(1);
    }

    @Test
    void findByItemIdAndSize() {
        itemSizePriceMapper.save(itemSizePriceInfo);
        assertThat(itemSizePriceMapper.findByItemIdAndSize(itemInfo.getId(), SIZE)).isEqualTo(
            itemSizePriceInfo);
    }

    @Test
    void delete() {
        itemSizePriceMapper.save(itemSizePriceInfo);
        assertThat(itemSizePriceMapper.delete(itemSizePriceInfo.getId())).isEqualTo(1);
    }
}