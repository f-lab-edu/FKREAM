package com.flab.fkream.deal;

import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DealMapper {

    int save(Deal deal);

    List<Deal> findByUserId(Long userId);

    Deal findById(Long id);

    int update(Deal deal);

    int delete(Long id);

    Deal findBuyNowDealByItemIdAndSizeAndPrice(Long id, String size, int price);

    Deal findSellNowDealByItemIdAndSizeAndPrice(Long id, String size, int price);

    Integer findHighestPurchasePriceByItemIdAndSize(Long itemId, String size);

    Integer findLowestSalePriceByItemIdAndSize(Long itemId, String size);

    List<MarketPriceDto> findMarketPrices(Long itemId, String size);

    List<MarketPriceDto> findMarketPricesInGraph(Long itemId, LocalDate period, String size);

    List<BiddingPriceDto> findBiddingPrices(Long itemId, String size, DealType dealType);

    List<DealHistoryCountDto> findHistoryCount(Long userId, DealType dealType);

    List<DealHistoryDto> findPurchaseHistories(Long userId, Status status);

    List<DealHistoryDto> findSaleHistories(Long userId, Status status);
}
