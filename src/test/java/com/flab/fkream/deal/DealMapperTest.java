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
import java.util.List;
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
                .releaseDate(LocalDate.now())
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

    @Test
    void findMarketPrices() {
        List<MarketPriceDto> marketPrices = dealMapper.findMarketPrices(13L, null);
        assertThat(marketPrices.get(0).getTradingDay()).isAfter(
            marketPrices.get(1).getTradingDay());
        List<MarketPriceDto> marketPrices2 = dealMapper.findMarketPrices(13L, "280");
        assertThat(marketPrices2.get(0).getSize()).isEqualTo("280");
    }

    @Test
    void findMarketPricesInGraph() {
        List<MarketPriceDto> marketPricesInGraph = dealMapper.findMarketPricesInGraph(71L,
            LocalDate.now().minusMonths(6), null);
        assertThat(marketPricesInGraph.get(0).getPrice()).isNotNull();
        assertThat(marketPricesInGraph.get(0).getSize()).isNotNull();
        assertThat(marketPricesInGraph.get(0).getTradingDay()).isNotNull();
        assertThat(marketPricesInGraph.get(0).getTradingDay()).isAfter(
            marketPricesInGraph.get(1).getTradingDay());
    }

    @Test
    void findBiddingPrices() {
        List<BiddingPriceDto> biddingPurchasePrices = dealMapper.findBiddingPrices(71L, null,
            KindOfDeal.PURCHASE);
        assertThat(biddingPurchasePrices.get(0).getPrice()).isGreaterThan(
            biddingPurchasePrices.get(1).getPrice());
        List<BiddingPriceDto> biddingSalePrices = dealMapper.findBiddingPrices(71L, null,
            KindOfDeal.SALE);
        assertThat(biddingSalePrices.get(0).getPrice()).isLessThan(
            biddingSalePrices.get(1).getPrice());
    }

    @Test
    void findHistoryCount() {
        List<DealHistoryCountDto> historyCount = dealMapper.findHistoryCount(30L,
            KindOfDeal.PURCHASE);
        assertThat(historyCount).hasSize(3);
    }

    @Test
    void findPurchaseHistories() {
        List<DealHistoryDto> purchaseHistories = dealMapper.findPurchaseHistories(30L,
            Status.COMPLETION);
        Deal deal = dealMapper.findById(purchaseHistories.get(0).getDealId());
        assertThat(deal.getUserId()).isEqualTo(30L);
        assertThat(deal.getKindOfDeal()).isEqualTo(KindOfDeal.PURCHASE);
        assertThat(deal.getStatus()).isEqualTo(Status.COMPLETION);
    }

    @Test
    void findSaleHistories() {
        List<DealHistoryDto> saleHistories = dealMapper.findSaleHistories(30L,
            Status.BIDDING);
        Deal deal = dealMapper.findById(saleHistories.get(0).getDealId());
        assertThat(deal.getUserId()).isEqualTo(30L);
        assertThat(deal.getKindOfDeal()).isEqualTo(KindOfDeal.SALE);
        assertThat(deal.getStatus()).isEqualTo(Status.BIDDING);
    }
}