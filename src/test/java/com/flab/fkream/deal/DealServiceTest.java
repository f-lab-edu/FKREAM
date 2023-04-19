package com.flab.fkream.deal;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.flab.fkream.brand.Brand;
import com.flab.fkream.item.Item;
import com.flab.fkream.item.ItemService;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.itemSizePrice.ItemSizePriceService;
import com.flab.fkream.utils.SessionUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DealServiceTest {

    @Mock
    DealMapper dealMapper;

    @Mock
    ItemSizePriceService itemSizePriceService;

    @Mock
    ItemService itemService;

    @InjectMocks
    DealService dealService;

    static MockedStatic<SessionUtil> sessionUtilities;

    @BeforeAll
    public static void beforeAll() {
        sessionUtilities = Mockito.mockStatic(SessionUtil.class);
    }

    @AfterAll
    public static void afterAll() {
        sessionUtilities.close();
    }

    Brand brand = Brand.builder().brandName("구찌").isLuxury(true).build();

    Item itemInfo =
        Item.builder()
            .id(1L)
            .itemName("나이키 에어포스")
            .modelNumber("NK22035")
            .category1("신발")
            .category2("스니커즈")
            .releaseDate(LocalDateTime.now())
            .representativeColor("Black")
            .releasedPrice(10000)
            .brand(brand)
            .build();

    ItemSizePrice itemSizePriceInfo = ItemSizePrice.builder()
        .id(1L)
        .itemId(1L)
        .size("250")
        .lowestSellingPrice(30000)
        .highestPurchasePrice(40000)
        .build();

    Deal dealInfo = Deal.builder()
        .id(1L)
        .item(itemInfo)
        .kindOfDeal(KindOfDeal.SALE)
        .userId(1L)
        .price(20000)
        .size("255")
        .period(LocalDate.now())
        .utilizationPolicy(true)
        .salesCondition(true)
        .status(Status.BIDDING)
        .build();

    @Test
    void saveSale_입찰() {
        Deal saleDealInfo = Deal.builder()
            .id(1L)
            .item(itemInfo)
            .kindOfDeal(KindOfDeal.SALE)
            .userId(1L)
            .price(45000)
            .size("255")
            .period(LocalDate.now())
            .utilizationPolicy(true)
            .salesCondition(true)
            .status(Status.BIDDING)
            .build();

        given(dealMapper.save(saleDealInfo)).willReturn(1);
        given(itemSizePriceService.findByItemIdAndSize(itemInfo.getId(),
            saleDealInfo.getSize())).willReturn(itemSizePriceInfo);
        dealService.sale(saleDealInfo);
        then(dealMapper).should().save(saleDealInfo);
        then(itemSizePriceService).should()
            .findByItemIdAndSize(itemInfo.getId(), saleDealInfo.getSize());
    }

    @Test
    void savePurchase_입찰() {
        Deal purchaseDealInfo = Deal.builder()
            .id(2L)
            .item(itemInfo)
            .kindOfDeal(KindOfDeal.PURCHASE)
            .userId(1L)
            .price(20000)
            .size("255")
            .period(LocalDate.now())
            .utilizationPolicy(true)
            .salesCondition(true)
            .status(Status.BIDDING)
            .build();

        given(dealMapper.save(purchaseDealInfo)).willReturn(1);
        given(itemSizePriceService.findByItemIdAndSize(itemInfo.getId(),
            purchaseDealInfo.getSize())).willReturn(itemSizePriceInfo);
        dealService.purchase(purchaseDealInfo);
        then(dealMapper).should().save(purchaseDealInfo);
        then(itemSizePriceService).should()
            .findByItemIdAndSize(itemInfo.getId(), purchaseDealInfo.getSize());
    }

    @Test
    void saveSale_거래() {
        Deal saleDealInfo = Deal.builder()
            .id(1L)
            .item(itemInfo)
            .kindOfDeal(KindOfDeal.SALE)
            .userId(1L)
            .price(40000)
            .size("255")
            .period(LocalDate.now())
            .utilizationPolicy(true)
            .salesCondition(true)
            .status(Status.BIDDING)
            .build();

        Deal otherDeal = Deal.builder()
            .id(1L)
            .item(itemInfo)
            .kindOfDeal(KindOfDeal.PURCHASE)
            .userId(2L)
            .price(40000)
            .size("255")
            .period(LocalDate.now())
            .utilizationPolicy(true)
            .salesCondition(true)
            .status(Status.BIDDING)
            .build();

        given(dealMapper.save(saleDealInfo)).willReturn(1);
        given(itemSizePriceService.findByItemIdAndSize(itemInfo.getId(),
            saleDealInfo.getSize())).willReturn(itemSizePriceInfo);
        given(dealMapper.findBuyNowDealByItemIdAndSizeAndPrice(saleDealInfo.getItem().getId(),
            saleDealInfo.getSize(), saleDealInfo.getPrice())).willReturn(otherDeal);
        given(dealMapper.update(otherDeal)).willReturn(1);
        given(itemService.findOne(saleDealInfo.getItem().getId())).willReturn(itemInfo);
        dealService.sale(saleDealInfo);
        then(dealMapper).should().save(saleDealInfo);
        then(itemSizePriceService).should()
            .findByItemIdAndSize(itemInfo.getId(), saleDealInfo.getSize());
        then(dealMapper).should()
            .findBuyNowDealByItemIdAndSizeAndPrice(saleDealInfo.getItem().getId(),
                saleDealInfo.getSize(), saleDealInfo.getPrice());
        then(dealMapper).should().update(otherDeal);
    }

    @Test
    void savePurchase_거래() {
        Deal purchaseDealInfo = Deal.builder()
            .id(2L)
            .item(itemInfo)
            .kindOfDeal(KindOfDeal.PURCHASE)
            .userId(1L)
            .price(30000)
            .size("255")
            .period(LocalDate.now())
            .utilizationPolicy(true)
            .salesCondition(true)
            .status(Status.BIDDING)
            .build();

        Deal otherDeal = Deal.builder()
            .id(1L)
            .item(itemInfo)
            .kindOfDeal(KindOfDeal.SALE)
            .userId(2L)
            .price(40000)
            .size("255")
            .period(LocalDate.now())
            .utilizationPolicy(true)
            .salesCondition(true)
            .status(Status.BIDDING)
            .build();

        given(dealMapper.save(purchaseDealInfo)).willReturn(1);
        given(itemSizePriceService.findByItemIdAndSize(itemInfo.getId(),
            purchaseDealInfo.getSize())).willReturn(itemSizePriceInfo);
        given(dealMapper.findSellNowDealByItemIdAndSizeAndPrice(purchaseDealInfo.getItem().getId(),
            purchaseDealInfo.getSize(), purchaseDealInfo.getPrice())).willReturn(otherDeal);
        given(dealMapper.update(otherDeal)).willReturn(1);
        given(itemService.findOne(purchaseDealInfo.getItem().getId())).willReturn(itemInfo);
        dealService.purchase(purchaseDealInfo);
        then(dealMapper).should().save(purchaseDealInfo);
        then(itemSizePriceService).should()
            .findByItemIdAndSize(itemInfo.getId(), purchaseDealInfo.getSize());
        then(dealMapper).should()
            .findSellNowDealByItemIdAndSizeAndPrice(purchaseDealInfo.getItem().getId(),
                purchaseDealInfo.getSize(), purchaseDealInfo.getPrice());
        then(dealMapper).should().update(otherDeal);
    }


    @Test
    void findByUserId() {
        given(dealMapper.findByUserId(1L)).willReturn(List.of(dealInfo));
        given(itemService.findOne(dealInfo.getItem().getId())).willReturn(itemInfo);
        assertThat(dealService.findByUserId()).contains(dealInfo);
    }

    @Test
    void findById() {
        given(dealMapper.findById(1L)).willReturn(dealInfo);
        given(itemService.findOne(dealInfo.getItem().getId())).willReturn(itemInfo);

        assertThat(dealService.findById(1L)).isEqualTo(dealInfo);
    }

    @Test
    void completeDeal() {
        given(dealMapper.findById(any())).willReturn(dealInfo);
        given(dealMapper.update(dealInfo)).willReturn(1);
        given(itemService.findOne(dealInfo.getItem().getId())).willReturn(itemInfo);

        dealService.completeDeal(1L);
        then(dealMapper).should(times(2)).update(dealInfo);
    }

    @Test
    void cancelDeal() {
        given(dealMapper.findById(any())).willReturn(dealInfo);
        given(dealMapper.update(dealInfo)).willReturn(1);
        given(itemService.findOne(dealInfo.getItem().getId())).willReturn(itemInfo);
        dealService.cancelDeal(1L);
        then(dealMapper).should(times(2)).update(dealInfo);
    }

    @Test
    void update() {
        given(dealMapper.update(dealInfo)).willReturn(1);
        sessionUtilities.when(SessionUtil::getLoginUserId).thenReturn(dealInfo.getUserId());
        dealService.update(dealInfo);
        then(dealMapper).should().update(dealInfo);
    }

    @Test
    void delete() {
        given(dealMapper.delete(1L)).willReturn(1);
        given(dealMapper.findById(1L)).willReturn(dealInfo);
        given(itemService.findOne(dealInfo.getItem().getId())).willReturn(itemInfo);
        sessionUtilities.when(SessionUtil::getLoginUserId).thenReturn(1L);
        dealService.delete(1L);
        then(dealMapper).should().delete(1L);
    }
}