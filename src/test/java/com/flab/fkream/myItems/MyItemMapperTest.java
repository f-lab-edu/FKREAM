package com.flab.fkream.myItems;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.fkream.item.Item;
import com.flab.fkream.mapper.ItemMapper;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.mapper.ItemSizePriceMapper;
import com.flab.fkream.mapper.MyItemMapper;
import com.flab.fkream.user.User;
import com.flab.fkream.mapper.UserMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@ActiveProfiles({"test"})
class MyItemMapperTest {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    ItemSizePriceMapper itemSizePriceMapper;

    @Autowired
    MyItemMapper myItemMapper;

    User user;
    Item item;
    ItemSizePrice itemSizePrice;
    MyItem myItem;

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

        myItem = MyItem.builder()
            .itemSizePriceId(itemSizePrice.getId())
            .userId(user.getId())
            .purchasePrice(50000)
            .build();
    }

    @Test
    void saveTest() throws Exception {
        assertThat(myItemMapper.save(myItem)).isEqualTo(1);
    }

    @Test
    void findOneTest() throws Exception {
        //given
        myItemMapper.save(myItem);

        //when
        MyItem result = myItemMapper.findOne(myItem.getId());

        //then
        assertThat(result).isEqualTo(myItem);

    }

    @Test
    void findAllByUserIdTest() throws Exception {
        //given
        ItemSizePrice itemSizePrice2 = ItemSizePrice.builder()
            .itemId(item.getId())
            .size("270")
            .build();
        itemSizePriceMapper.save(itemSizePrice2);

        MyItem myItem2 = MyItem.builder()
            .itemSizePriceId(itemSizePrice2.getId())
            .userId(user.getId())
            .purchasePrice(60000)
            .build();

        myItemMapper.save(myItem);
        myItemMapper.save(myItem2);

        //when
        List<MyItem> myItems = myItemMapper.findAllByUserId(user.getId());

        //then
        assertThat(myItems.size()).isEqualTo(2);

    }


    @Test
    void updateTest() {
        // given
        myItemMapper.save(myItem);

        ItemSizePrice newItemSizePrice = ItemSizePrice.builder()
            .itemId(item.getId())
            .size("270")
            .build();
        itemSizePriceMapper.save(newItemSizePrice);

        MyItem updatedMyItem = MyItem.builder()
            .id(myItem.getId())
            .userId(user.getId())
            .itemSizePriceId(newItemSizePrice.getId())
            .purchasePrice(30000)
            .build();

        // when
        int result = myItemMapper.update(updatedMyItem);

        // then
        assertThat(result).isEqualTo(1);

    }

    @Test
    void deleteTest() {
        // given
        myItemMapper.save(myItem);
        Long ownedItemId = myItem.getId();

        // when
        int result = myItemMapper.delete(ownedItemId);

        // then
        assertThat(result).isEqualTo(1);

    }
}