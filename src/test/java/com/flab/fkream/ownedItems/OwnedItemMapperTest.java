package com.flab.fkream.ownedItems;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.fkream.item.Item;
import com.flab.fkream.item.ItemMapper;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.itemSizePrice.ItemSizePriceMapper;
import com.flab.fkream.user.User;
import com.flab.fkream.user.UserMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class OwnedItemMapperTest {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    ItemSizePriceMapper itemSizePriceMapper;

    @Autowired
    OwnedItemMapper ownedItemMapper;

    User user;
    Item item;
    ItemSizePrice itemSizePrice;
    OwnedItem ownedItem;

    @BeforeEach
    public void setUp() {
        user = User.builder()
            .email("test1")
            .password("000")
            .gender("aa")
            .name("test")
            .adAgreement(true)
            .fourteenAgreement(true)
            .personalAuthentication(true)
            .phoneNumber("010")
            .build();
        userMapper.save(user);

        item = Item.builder()
            .itemName("나이키 에어포스")
            .modelNumber("NK22035")
            .representativeColor("Black")
            .releasedPrice(10000)
            .build();
        itemMapper.save(item);

        itemSizePrice = ItemSizePrice.builder()
            .itemId(item.getId())
            .size("250")
            .build();
        itemSizePriceMapper.save(itemSizePrice);

        ownedItem = OwnedItem.builder()
            .itemSizePriceId(itemSizePrice.getId())
            .userId(user.getId())
            .purchasePrice(50000)
            .build();
    }

    @Test
    void saveTest() throws Exception {
        assertThat(ownedItemMapper.save(ownedItem)).isEqualTo(1);
    }

    @Test
    void findOneTest() throws Exception {
        //given
        ownedItemMapper.save(ownedItem);

        //when
        OwnedItem result = ownedItemMapper.findOne(ownedItem.getId());

        //then
        assertThat(result).isEqualTo(ownedItem);

    }

    @Test
    void findAllByUserIdTest() throws Exception {
        //given
        ItemSizePrice itemSizePrice2 = ItemSizePrice.builder()
            .itemId(item.getId())
            .size("270")
            .build();
        itemSizePriceMapper.save(itemSizePrice2);

        OwnedItem ownedItem2 = OwnedItem.builder()
            .itemSizePriceId(itemSizePrice2.getId())
            .userId(user.getId())
            .purchasePrice(60000)
            .build();

        ownedItemMapper.save(ownedItem);
        ownedItemMapper.save(ownedItem2);

        //when
        List<OwnedItem> ownedItems = ownedItemMapper.findAllByUserId(user.getId());

        //then
        assertThat(ownedItems.size()).isEqualTo(2);

    }


    @Test
    void updateTest() {
        // given
        ownedItemMapper.save(ownedItem);

        ItemSizePrice newItemSizePrice = ItemSizePrice.builder()
            .itemId(item.getId())
            .size("270")
            .build();
        itemSizePriceMapper.save(newItemSizePrice);

        OwnedItem updatedOwnedItem = OwnedItem.builder()
            .id(ownedItem.getId())
            .userId(user.getId())
            .itemSizePriceId(newItemSizePrice.getId())
            .purchasePrice(30000)
            .build();

        // when
        int result = ownedItemMapper.update(updatedOwnedItem);

        // then
        assertThat(result).isEqualTo(1);

    }

    @Test
    void deleteTest() {
        // given
        ownedItemMapper.save(ownedItem);
        Long ownedItemId = ownedItem.getId();

        // when
        int result = ownedItemMapper.delete(ownedItemId);

        // then
        assertThat(result).isEqualTo(1);

    }
}