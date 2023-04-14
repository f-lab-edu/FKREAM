package com.flab.fkream.deal;

import static org.assertj.core.api.Assertions.*;

import com.flab.fkream.brand.Brand;
import com.flab.fkream.brand.BrandMapper;
import com.flab.fkream.item.Item;
import com.flab.fkream.item.ItemMapper;
import com.flab.fkream.itemSizePrice.ItemSizePriceMapper;
import com.flab.fkream.user.User;
import com.flab.fkream.user.UserMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class DealMapperTest {

    @Autowired
    DealMapper dealMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BrandMapper brandMapper;

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    ItemSizePriceMapper itemSizePriceMapper;

    Brand brandInfo;
    Item itemInfo;
    User userInfo;
    Deal saleDealInfo;
    Deal purchaseDealInfo;

    @BeforeEach
    void setUp() {
        brandInfo = Brand.builder().brandName("구찌").isLuxury(true).build();
        brandMapper.save(brandInfo);
        itemInfo =
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
        itemMapper.save(itemInfo);
        userInfo = User.builder()
            .email("test1")
            .password("000")
            .gender("aa")
            .name("test")
            .adAgreement(true)
            .fourteenAgreement(true)
            .personalAuthentication(true)
            .phoneNumber("010")
            .build();
        userMapper.save(userInfo);

        saleDealInfo = Deal.builder()
            .item(itemInfo)
            .kindOfDeal(KindOfDeal.SALE)
            .userId(userInfo.getId())
            .price(20000)
            .size("255")
            .period(LocalDate.now())
            .utilizationPolicy(true)
            .salesCondition(true)
            .status(Status.BIDDING)
            .build();

        purchaseDealInfo = Deal.builder()
            .item(itemInfo)
            .kindOfDeal(KindOfDeal.PURCHASE)
            .userId(userInfo.getId())
            .price(20000)
            .size("255")
            .period(LocalDate.now())
            .utilizationPolicy(true)
            .salesCondition(true)
            .status(Status.BIDDING)
            .build();
    }

    @Test
    void save() {
        assertThat(dealMapper.save(saleDealInfo)).isEqualTo(1);
    }

    @Test
    void findByUserId() {
        dealMapper.save(saleDealInfo);
        assertThat(dealMapper.findByUserId(saleDealInfo.getUserId())).contains(saleDealInfo);
    }

    @Test
    void findById() {
        dealMapper.save(saleDealInfo);
        assertThat(dealMapper.findById(saleDealInfo.getId())).isEqualTo(saleDealInfo);
    }

    @Test
    void update() {
        dealMapper.save(saleDealInfo);
        Deal dealInfo = Deal.builder()
            .id(saleDealInfo.getId())
            .item(itemInfo)
            .kindOfDeal(KindOfDeal.SALE)
            .userId(userInfo.getId())
            .price(30000)
            .size("260")
            .period(LocalDate.now())
            .utilizationPolicy(true)
            .salesCondition(true)
            .status(Status.BIDDING)
            .build();
        assertThat(dealMapper.update(dealInfo)).isEqualTo(1);
    }

    @Test
    void delete() {
        dealMapper.save(saleDealInfo);
        assertThat(dealMapper.delete(saleDealInfo.getId())).isEqualTo(1);
    }

    @Test
    void findBidToBuyDealByItemIdAndSize() {
        dealMapper.save(purchaseDealInfo);
        assertThat(
            dealMapper.findBuyNowDealByItemIdAndSizeAndPrice(purchaseDealInfo.getItem().getId(),
                purchaseDealInfo.getSize(), purchaseDealInfo.getPrice())).isEqualTo(
            purchaseDealInfo);
    }

    @Test
    void findBidToSellDealByItemIdAndSize() {
        dealMapper.save(saleDealInfo);
        assertThat(dealMapper.findSellNowDealByItemIdAndSizeAndPrice(saleDealInfo.getItem().getId(),
            saleDealInfo.getSize(), saleDealInfo.getPrice())).isEqualTo(saleDealInfo);
    }

    @Test
    void findHighestPurchasePriceByItemIdAndSize() {
        dealMapper.save(purchaseDealInfo);
        Deal otherDeal = Deal.builder()
            .item(itemInfo)
            .kindOfDeal(KindOfDeal.PURCHASE)
            .userId(userInfo.getId())
            .price(30000)
            .size("255")
            .period(LocalDate.now())
            .utilizationPolicy(true)
            .salesCondition(true)
            .status(Status.BIDDING)
            .build();
        dealMapper.save(otherDeal);
        assertThat(dealMapper.findHighestPurchasePriceByItemIdAndSize(itemInfo.getId(),
            purchaseDealInfo.getSize())).isEqualTo(otherDeal.getPrice());
    }

    @Test
    void findLowestSalePriceByItemIdAndSize() {
        dealMapper.save(saleDealInfo);
        Deal otherDeal = Deal.builder()
            .item(itemInfo)
            .kindOfDeal(KindOfDeal.SALE)
            .userId(userInfo.getId())
            .price(10000)
            .size("255")
            .period(LocalDate.now())
            .utilizationPolicy(true)
            .salesCondition(true)
            .status(Status.BIDDING)
            .build();
        dealMapper.save(otherDeal);
        assertThat(dealMapper.findLowestSalePriceByItemIdAndSize(itemInfo.getId(),
            saleDealInfo.getSize())).isEqualTo(otherDeal.getPrice());
    }
}