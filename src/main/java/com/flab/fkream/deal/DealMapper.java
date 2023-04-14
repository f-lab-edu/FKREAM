package com.flab.fkream.deal;

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

    int findHighestPurchasePriceByItemIdAndSize(Long itemId, String size);

    int findLowestSalePriceByItemIdAndSize(Long itemId, String size);
}
