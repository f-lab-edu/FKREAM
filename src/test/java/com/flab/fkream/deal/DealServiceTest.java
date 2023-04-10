package com.flab.fkream.deal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.flab.fkream.brand.Brand;
import com.flab.fkream.item.Item;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.itemSizePrice.ItemSizePriceService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DealServiceTest {

    @Mock
    DealMapper dealMapper;

    @Mock
    ItemSizePriceService itemSizePriceService;

    @InjectMocks
    DealService dealService;

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
            .price(20000)
            .size("255")
            .period(LocalDate.now())
            .utilizationPolicy(true)
            .salesCondition(true)
            .status(Status.BIDDING)
            .build();

        given(dealMapper.save(saleDealInfo)).willReturn(1);
        given(itemSizePriceService.findByItemIdAndSize(itemInfo.getId(),
            saleDealInfo.getSize())).willReturn(itemSizePriceInfo);
        dealService.saveSale(saleDealInfo);
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
        dealService.savePurchase(purchaseDealInfo);
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
        given(dealMapper.findBidToBuyDealByItemIdAndSize(saleDealInfo.getItem().getId(),
            saleDealInfo.getSize())).willReturn(otherDeal);
        given(dealMapper.update(otherDeal)).willReturn(1);
        dealService.saveSale(saleDealInfo);
        then(dealMapper).should().save(saleDealInfo);
        then(itemSizePriceService).should()
            .findByItemIdAndSize(itemInfo.getId(), saleDealInfo.getSize());
        then(dealMapper).should().findBidToBuyDealByItemIdAndSize(saleDealInfo.getItem().getId(),
            saleDealInfo.getSize());
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
        given(dealMapper.findBidToSellDealByItemIdAndSize(purchaseDealInfo.getItem().getId(),
            purchaseDealInfo.getSize())).willReturn(otherDeal);
        given(dealMapper.update(otherDeal)).willReturn(1);
        dealService.savePurchase(purchaseDealInfo);
        then(dealMapper).should().save(purchaseDealInfo);
        then(itemSizePriceService).should()
            .findByItemIdAndSize(itemInfo.getId(), purchaseDealInfo.getSize());
        then(dealMapper).should()
            .findBidToSellDealByItemIdAndSize(purchaseDealInfo.getItem().getId(),
                purchaseDealInfo.getSize());
        then(dealMapper).should().update(otherDeal);
    }


    @Test
    void findByUserId() {
        given(dealMapper.findByUserId(1L)).willReturn(List.of(dealInfo));
        assertThat(dealService.findByUserId(1L)).contains(dealInfo);
    }

    @Test
    void findById() {
        given(dealMapper.findById(1L)).willReturn(dealInfo);
        assertThat(dealService.findById(1L)).isEqualTo(dealInfo);
    }

    @Test
    void completeDeal() {
        given(dealMapper.findById(any())).willReturn(dealInfo);
        given(dealMapper.update(dealInfo)).willReturn(1);
        dealService.completeDeal(1L);
        then(dealMapper).should(times(2)).update(dealInfo);
    }

    @Test
    void cancelDeal() {
        given(dealMapper.findById(any())).willReturn(dealInfo);
        given(dealMapper.update(dealInfo)).willReturn(1);
        dealService.cancelDeal(1L);
        then(dealMapper).should(times(2)).update(dealInfo);
    }

    @Test
    void update() {
        given(dealMapper.update(dealInfo)).willReturn(1);
        dealService.update(dealInfo);
        then(dealMapper).should().update(dealInfo);
    }

    @Test
    void delete() {
        given(dealMapper.delete(1L)).willReturn(1);
        dealService.delete(1L);
        then(dealMapper).should().delete(1L);
    }
}