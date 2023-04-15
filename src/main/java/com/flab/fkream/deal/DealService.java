package com.flab.fkream.deal;


import com.flab.fkream.error.exception.NoDataFoundException;
import com.flab.fkream.error.exception.NoRequestHigherPriceThenImmediatePurchaseException;
import com.flab.fkream.error.exception.NoRequestLowerPriceThenImmediateSaleException;
import com.flab.fkream.error.exception.NotOwnedDataException;
import com.flab.fkream.item.ItemService;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.itemSizePrice.ItemSizePriceService;
import com.flab.fkream.utils.SessionUtil;
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
    public void sale(Deal deal) {
        deal.setKindOfDealToSale();
        deal.setCreatedAtToNow();
        ItemSizePrice itemSizePrice = itemSizePriceService.findByItemIdAndSize(
            deal.getItem().getId(), deal.getSize());
        if (itemSizePrice.getHighestPurchasePrice() == 0
            || deal.getPrice() > itemSizePrice.getHighestPurchasePrice()) {
            bidSale(deal);
        }
        if (deal.getPrice() == itemSizePrice.getHighestPurchasePrice()) {
            immediateSale(deal);
        }
        if (deal.getPrice() < itemSizePrice.getHighestPurchasePrice()) {
            throw new NoRequestLowerPriceThenImmediateSaleException();
        }
        updatePrice(deal, itemSizePrice);
    }

    @Transactional
    public void purchase(Deal deal) {
        deal.setKindOfDealToPurchase();
        deal.setCreatedAtToNow();
        ItemSizePrice itemSizePrice = itemSizePriceService.findByItemIdAndSize(
            deal.getItem().getId(), deal.getSize());
        if (itemSizePrice.getLowestSellingPrice() == 0
            || deal.getPrice() < itemSizePrice.getLowestSellingPrice()) {
            bidPurchase(deal);
        }
        if (deal.getPrice() == itemSizePrice.getLowestSellingPrice()) {
            immediatePurchase(deal);
        }
        if (deal.getPrice() > itemSizePrice.getLowestSellingPrice()) {
            throw new NoRequestHigherPriceThenImmediatePurchaseException();
        }
        updatePrice(deal, itemSizePrice);
    }

    public List<Deal> findByUserId() {
        Long userId = SessionUtil.getLoginUserId();
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
        if (deal.getUserId() != SessionUtil.getLoginUserId()) {
            throw new NotOwnedDataException();
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
        if (deal.getUserId() != SessionUtil.getLoginUserId()) {
            throw new NotOwnedDataException();
        }
        dealMapper.update(deal);
    }

    public void delete(Long id) {
        findById(id);
        dealMapper.delete(id);
    }

    private void immediateSale(Deal deal) {
        deal.setStatus(Status.PROGRESS);
        Deal purchaseHistory = findBuyNowDeal(deal);
        purchaseHistory.setStatus(Status.PROGRESS);
        deal.setOtherId(purchaseHistory.getId());
        dealMapper.save(deal);
        purchaseHistory.setOtherId(deal.getId());
        dealMapper.update(purchaseHistory);
    }

    private void immediatePurchase(Deal deal) {
        deal.setStatus(Status.PROGRESS);
        Deal saleHistory = findSellNowDeal(deal);
        saleHistory.setStatus(Status.PROGRESS);
        deal.setOtherId(saleHistory.getId());
        dealMapper.save(deal);
        saleHistory.setOtherId(deal.getId());
        dealMapper.update(saleHistory);
    }

    private void bidSale(Deal deal) {
        deal.setStatus(Status.BIDDING);
        dealMapper.save(deal);
    }

    private void bidPurchase(Deal deal) {
        deal.setStatus(Status.BIDDING);
        dealMapper.save(deal);
    }

    private void updatePrice(Deal deal, ItemSizePrice itemSizePrice) {
        if (deal.getStatus() == Status.BIDDING) {
            if (deal.getKindOfDeal() == KindOfDeal.PURCHASE) {
                if (deal.getPrice() > itemSizePrice.getHighestPurchasePrice()) {
                    itemSizePrice.setHighestPurchasePrice(deal.getPrice());
                }
            }
            if (deal.getKindOfDeal() == KindOfDeal.SALE) {
                if (deal.getPrice() < itemSizePrice.getLowestSellingPrice()) {
                    itemSizePrice.setLowestSellingPrice(deal.getPrice());
                }
            }
        }
        if (deal.getStatus() == Status.PROGRESS) {
            int highestPurchasePrice = dealMapper.findHighestPurchasePriceByItemIdAndSize(
                itemSizePrice.getItemId(), itemSizePrice.getSize());
            int lowestSalePrice = dealMapper.findLowestSalePriceByItemIdAndSize(
                itemSizePrice.getItemId(), itemSizePrice.getSize());
            itemSizePrice.changePrice(highestPurchasePrice, lowestSalePrice);
        }
        itemSizePriceService.update(itemSizePrice);
    }

    private Deal findBuyNowDeal(Deal deal) {
        Deal purchaseHistory = dealMapper.findBuyNowDealByItemIdAndSizeAndPrice(
            deal.getItem().getId(), deal.getSize(), deal.getPrice());
        if (purchaseHistory == null) {
            throw new NoDataFoundException();
        }
        purchaseHistory.setItem(itemService.findOne(deal.getItem().getId()));
        return purchaseHistory;
    }

    private Deal findSellNowDeal(Deal deal) {
        Deal saleHistory = dealMapper.findSellNowDealByItemIdAndSizeAndPrice(deal.getItem().getId(),
            deal.getSize(), deal.getPrice());
        if (saleHistory == null) {
            throw new NoDataFoundException();
        }
        saleHistory.setItem(itemService.findOne(deal.getItem().getId()));
        return saleHistory;
    }
}
