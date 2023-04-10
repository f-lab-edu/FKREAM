package com.flab.fkream.deal;


import com.flab.fkream.error.exception.NoDataFoundException;
import com.flab.fkream.item.ItemService;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.itemSizePrice.ItemSizePriceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class DealService {

    private final DealMapper dealMapper;

    private final ItemService itemService;

    private final ItemSizePriceService itemSizePriceService;


    @Transactional
    public void saveSale(Deal deal) {
        deal.setKindOfDealToSale();
        deal.setCreatedAtToNow();
        ItemSizePrice itemSizePrice = itemSizePriceService.findByItemIdAndSize(
            deal.getItem().getId(), deal.getSize());
        BiddingSaleDeal(deal, itemSizePrice);
        ProgressSaleDeal(deal, itemSizePrice);
    }

    @Transactional
    public void savePurchase(Deal deal) {
        deal.setKindOfDealToPurchase();
        deal.setCreatedAtToNow();
        ItemSizePrice itemSizePrice = itemSizePriceService.findByItemIdAndSize(
            deal.getItem().getId(), deal.getSize());
        BiddingPurchaseDeal(deal, itemSizePrice);
        ProgressPurchaseDeal(deal, itemSizePrice);
    }


    private void BiddingSaleDeal(Deal deal, ItemSizePrice itemSizePrice) {
        if (deal.getPrice() != itemSizePrice.getHighestPurchasePrice()) {
            deal.setStatus(Status.BIDDING);
            dealMapper.save(deal);
        }
    }


    private void BiddingPurchaseDeal(Deal deal, ItemSizePrice itemSizePrice) {
        if (deal.getPrice() != itemSizePrice.getLowestSellingPrice()) {
            deal.setStatus(Status.BIDDING);
            dealMapper.save(deal);
        }
    }


    private void ProgressSaleDeal(Deal deal, ItemSizePrice itemSizePrice) {
        if (deal.getPrice() == itemSizePrice.getHighestPurchasePrice()) {
            deal.setStatus(Status.PROGRESS);
            Deal dealPurchased = findBidToBuyDealByItemIdAndSize(deal.getItem().getId(),
                deal.getSize());
            dealPurchased.setStatus(Status.PROGRESS);
            deal.setOtherId(dealPurchased.getId());
            dealMapper.save(deal);
            dealPurchased.setOtherId(deal.getId());
            dealMapper.update(dealPurchased);
            updatePrice(itemSizePrice);
        }
    }

    private void ProgressPurchaseDeal(Deal deal, ItemSizePrice itemSizePrice) {
        if (deal.getPrice() == itemSizePrice.getLowestSellingPrice()) {
            deal.setStatus(Status.PROGRESS);
            Deal dealPurchased = findBidToSellDealByItemIdAndSize(deal.getItem().getId(),
                deal.getSize());
            dealPurchased.setStatus(Status.PROGRESS);
            deal.setOtherId(dealPurchased.getId());
            dealMapper.save(deal);
            dealPurchased.setOtherId(deal.getId());
            dealMapper.update(dealPurchased);
            updatePrice(itemSizePrice);
        }
    }

    private void updatePrice(ItemSizePrice itemSizePrice) {
        int highestPurchasePrice = dealMapper.findHighestPurchasePriceByItemIdAndSize(itemSizePrice.getItemId(), itemSizePrice.getSize());
        int lowestSalePrice = dealMapper.findLowestSalePriceByItemIdAndSize(itemSizePrice.getItemId(), itemSizePrice.getSize());
        itemSizePrice.changePrice(highestPurchasePrice, lowestSalePrice);
        itemSizePriceService.update(itemSizePrice);
    }

    private Deal findBidToBuyDealByItemIdAndSize(Long id, String size) {
        Deal deal = dealMapper.findBidToBuyDealByItemIdAndSize(id, size);
        if (deal == null) {
            throw new NoDataFoundException();
        }
        deal.setItem(itemService.findOne(deal.getItem().getId()));
        return deal;
    }

    private Deal findBidToSellDealByItemIdAndSize(Long id, String size) {
        Deal deal = dealMapper.findBidToSellDealByItemIdAndSize(id, size);
        if (deal == null) {
            throw new NoDataFoundException();
        }
        deal.setItem(itemService.findOne(deal.getItem().getId()));
        return deal;
    }

    public List<Deal> findByUserId(Long userId) {
        List<Deal> deals = dealMapper.findByUserId(userId);
        if (deals.size() == 0) {
            throw new NoDataFoundException();
        }
        for (Deal deal : deals) {
            deal.setItem(itemService.findOne(deal.getItem().getId()));
        }
        return deals;
    }

    public Deal findById(Long id) {
        Deal deal = dealMapper.findById(id);
        if (deal == null) {
            throw new NoDataFoundException();
        }
        deal.setItem(itemService.findOne(deal.getItem().getId()));
        return deal;
    }

    @Transactional
    public void completeDeal(Long id) {
        Deal deal = findById(id);
        deal.setStatus(Status.COMPLETION);
        Deal otherDeal = findById(deal.getOtherId());
        otherDeal.setStatus(Status.COMPLETION);
        update(deal);
        update(otherDeal);
    }

    @Transactional
    public void cancelDeal(Long id) {
        Deal deal = findById(id);
        deal.setStatus(Status.CANCEL);
        Deal otherDeal = findById(deal.getOtherId());
        otherDeal.setStatus(Status.CANCEL);
        update(deal);
        update(otherDeal);
    }

    public void update(Deal deal) {
        dealMapper.update(deal);
    }

    public void delete(Long id) {
        dealMapper.delete(id);
    }
}
