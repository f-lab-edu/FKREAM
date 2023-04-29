package com.flab.fkream.interestedItem;

import com.flab.fkream.item.Item;
import com.flab.fkream.item.ItemMapper;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.itemSizePrice.ItemSizePriceMapper;
import com.flab.fkream.user.User;
import com.flab.fkream.user.UserMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class
InterestedItemMapperTest {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    ItemSizePriceMapper itemSizePriceMapper;

    @Autowired
    InterestedItemMapper interestedItemMapper;

    private User user;
    private Item item;
    private ItemSizePrice itemSizePrice;
    private InterestedItem interestedItem;

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

        interestedItem = InterestedItem.builder()
            .itemSizePriceId(itemSizePrice.getId())
            .userId(user.getId())
            .build();
        interestedItemMapper.save(interestedItem);
    }

    @Test
    void save() {
        //when
        interestedItemMapper.save(interestedItem);

        //then
        Assertions.assertThat(interestedItem.getId()).isNotNull();
    }

    @Test
    void findUserIdsByItemSizePriceID() {
        User user2 = User.builder()
            .email("test2")
            .password("000")
            .gender("aa")
            .name("test2")
            .adAgreement(true)
            .fourteenAgreement(true)
            .personalAuthentication(true)
            .phoneNumber("010")
            .build();
        userMapper.save(user2);
        InterestedItem anotherInterestedItem = InterestedItem.builder()
            .itemSizePriceId(itemSizePrice.getId())
            .userId(user2.getId())
            .build();
        interestedItemMapper.save(anotherInterestedItem);

        List<Long> userIdsByItemSizePriceID = interestedItemMapper.findUserIdsByItemSizePriceID(
            itemSizePrice.getId());
        Assertions.assertThat(userIdsByItemSizePriceID).hasSize(2);
    }

    @Test
    void findAllByUserId() throws Exception {
        //given
        ItemSizePrice anotherItemSizePrice = ItemSizePrice.builder()
            .itemId(item.getId())
            .size("250")
            .build();
        itemSizePriceMapper.save(anotherItemSizePrice);

        InterestedItem anotherInterestedItem = InterestedItem.builder()
            .itemSizePriceId(anotherItemSizePrice.getId())
            .userId(user.getId())
            .build();
        interestedItemMapper.save(anotherInterestedItem);

        //when
        List<InterestedItem> interestedItems = interestedItemMapper.findAllByUserId(user.getId());

        //then
        Assertions.assertThat(interestedItems.size()).isEqualTo(2);
    }

    @Test
    void deleteById() {
        //when
        Long userId = interestedItem.getUserId();
        Long itemSizePriceId = interestedItem.getItemSizePriceId();
        System.out.println("userId = " + userId);
        System.out.println("itemSizePriceId = " + itemSizePriceId);
        int affectedRow = interestedItemMapper.deleteById(userId, itemSizePriceId);

        //then
        Assertions.assertThat(affectedRow).isEqualTo(1);
    }

}