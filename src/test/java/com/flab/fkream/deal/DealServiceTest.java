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

    Brand brand = Brand.builder().brandName("구찌").luxury(true).build();

    Item itemInfo =
        Item.builder()
            .id(1L)
            .itemName("나이키 에어포스")
            .modelNumber("NK22035")
            .releaseDate(LocalDate.now())
            .representativeColor("Black")
            .releasedPrice(10000)
            .brand(brand)
            .build();

    ItemSizePrice itemSizePriceInfo = ItemSizePrice.builder()
        .id(1L)
        .itemId(1L)
        .size("250")
        .immediatePurchasePrice(30000)
        .immediateSalePrice(40000)
        .build();

    Deal dealInfo = Deal.builder()
        .id(1L)
        .item(itemInfo)
        .dealType(DealType.SALE)
        .userId(1L)
        .price(20000)
        .size("255")
        .period(LocalDate.now())
        .utilizationPolicy(true)
        .salesCondition(true)
        .dealStatus(DealStatus.BIDDING)
        .build();

    MarketPriceDto marketPriceDto = MarketPriceDto.builder().size("260").build();
    BiddingPriceDto biddingPriceDto = BiddingPriceDto.builder().build();
    DealHistoryDto dealHistoryDto = DealHistoryDto.builder().build();
    DealHistoryCountDto dealHistoryCountDto = DealHistoryCountDto.builder()
        .dealStatus(DealStatus.COMPLETION).count(5).build();

    @Test
    void saveSale_입찰() {
        Deal saleDealInfo = Deal.builder()
            .id(1L)
            .item(itemInfo)
            .dealType(DealType.SALE)
            .userId(1L)
            .price(45000)
            .size("255")
            .period(LocalDate.now())
            .utilizationPolicy(true)
            .salesCondition(true)
            .dealStatus(DealStatus.BIDDING)
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
            .dealType(DealType.PURCHASE)
            .userId(1L)
            .price(20000)
            .size("255")
            .period(LocalDate.now())
            .utilizationPolicy(true)
            .salesCondition(true)
            .dealStatus(DealStatus.BIDDING)
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
            .dealType(DealType.SALE)
            .userId(1L)
            .price(40000)
            .size("255")
            .period(LocalDate.now())
            .utilizationPolicy(true)
            .salesCondition(true)
            .dealStatus(DealStatus.BIDDING)
            .build();

        Deal otherDeal = Deal.builder()
            .id(1L)
            .item(itemInfo)
            .dealType(DealType.PURCHASE)
            .userId(2L)
            .price(40000)
            .size("255")
            .period(LocalDate.now())
            .utilizationPolicy(true)
            .salesCondition(true)
            .dealStatus(DealStatus.BIDDING)
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
            .dealType(DealType.PURCHASE)
            .userId(1L)
            .price(30000)
            .size("255")
            .period(LocalDate.now())
            .utilizationPolicy(true)
            .salesCondition(true)
            .dealStatus(DealStatus.BIDDING)
            .build();

        Deal otherDeal = Deal.builder()
            .id(1L)
            .item(itemInfo)
            .dealType(DealType.SALE)
            .userId(2L)
            .price(40000)
            .size("255")
            .period(LocalDate.now())
            .utilizationPolicy(true)
            .salesCondition(true)
            .dealStatus(DealStatus.BIDDING)
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

    @Test
    void findMarketPriceInGraph() {
        given(dealMapper.findMarketPricesInGraph(1L, LocalDate.now().minusMonths(1), "260"))
            .willReturn(List.of(marketPriceDto));
        assertThat(dealService.findMarketPriceInGraph(1L, DealPeriod.ONE_MONTH, "260")).contains(marketPriceDto);
        then(dealMapper).should().findMarketPricesInGraph(1L, LocalDate.now().minusMonths(1), "260");
    }

    @Test
    void findMarketPrices() {
        given(dealMapper.findMarketPrices(1L, "255")).willReturn(List.of(marketPriceDto));
        assertThat(dealService.findMarketPrices(1L, "255")).contains(marketPriceDto);
        then(dealMapper).should().findMarketPrices(1L, "255");
    }

    @Test
    void findBiddingPrices() {
        given(dealMapper.findBiddingPrices(1L, "255", DealType.SALE)).willReturn(
            List.of(biddingPriceDto));
        assertThat(dealService.findBiddingPrices(1L, "255", DealType.SALE)).contains(
            biddingPriceDto);
        then(dealMapper).should().findBiddingPrices(1L, "255", DealType.SALE);
    }

    @Test
    void findHistoryCount() {
        sessionUtilities.when(SessionUtil::getLoginUserId).thenReturn(1L);
        given(dealMapper.findHistoryCount(1L, DealType.SALE)).willReturn(
            List.of(dealHistoryCountDto));
        assertThat(dealService.findHistoryCount(DealType.SALE)).containsEntry(DealStatus.COMPLETION,
            5);
        then(dealMapper).should().findHistoryCount(1L, DealType.SALE);
    }

    @Test
    void findPurchaseHistory() {
        sessionUtilities.when(SessionUtil::getLoginUserId).thenReturn(1L);
        given(dealMapper.findPurchaseHistories(1L, DealStatus.COMPLETION)).willReturn(
            List.of(dealHistoryDto));
        assertThat(dealService.findPurchaseHistories(DealStatus.COMPLETION)).contains(dealHistoryDto);
        then(dealMapper).should().findPurchaseHistories(1L, DealStatus.COMPLETION);
    }

    @Test
    void findSaleHistory() {
        sessionUtilities.when(SessionUtil::getLoginUserId).thenReturn(1L);
        given(dealMapper.findSaleHistories(1L, DealStatus.COMPLETION)).willReturn(
            List.of(dealHistoryDto));
        assertThat(dealService.findSaleHistories(DealStatus.COMPLETION)).contains(dealHistoryDto);
        then(dealMapper).should().findSaleHistories(1L, DealStatus.COMPLETION);
    }
}