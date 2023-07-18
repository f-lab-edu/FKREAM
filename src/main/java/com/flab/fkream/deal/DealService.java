package com.flab.fkream.deal;


import com.flab.fkream.error.exception.NoDataFoundException;
import com.flab.fkream.error.exception.NoMatchDealStatusException;
import com.flab.fkream.error.exception.NoMatchDealTypeException;
import com.flab.fkream.error.exception.NoRequestHigherPriceThenImmediatePurchaseException;
import com.flab.fkream.error.exception.NoRequestLowerPriceThenImmediateSaleException;
import com.flab.fkream.error.exception.NotOwnedDataException;
import com.flab.fkream.item.ItemService;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.itemSizePrice.ItemSizePriceService;
import com.flab.fkream.kafka.KafkaMessageSender;
import com.flab.fkream.kafka.KafkaTopic;
import com.flab.fkream.utils.SessionUtil;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class DealService {

    private final DealMapper dealMapper;
    private final ItemService itemService;
    private final ItemSizePriceService itemSizePriceService;
    private final RedissonClient redissonClient;
    private final KafkaMessageSender messageSender;


    @Transactional
    public void sale(Deal deal) {
        validateDealType(deal, DealType.SALE);
        deal.setCreatedAtToNow();

        ItemSizePrice itemSizePrice = findItemSizePrice(deal);

        switch (deal.getDealStatus()) {
            case BIDDING:
                handleBiddingSale(deal, itemSizePrice);
                break;
            case IMMEDIATE:
                handleImmediateSale(deal, itemSizePrice);
                break;
            default:
                throw new NoMatchDealStatusException("잘못된 딜 타입입니다.");
        }

        messageSender.send(KafkaTopic.ITEM_PURCHASE_PRICE, itemSizePrice);
    }

    @Transactional
    public void purchase(Deal deal) {
        validateDealType(deal, DealType.PURCHASE);
        deal.setCreatedAtToNow();

        ItemSizePrice itemSizePrice = findItemSizePrice(deal);

        switch (deal.getDealStatus()) {
            case BIDDING:
                handleBiddingPurchase(deal, itemSizePrice);
                break;
            case IMMEDIATE:
                handleImmediatePurchase(deal, itemSizePrice);
                break;
            default:
                throw new NoMatchDealStatusException("잘못된 딜 타입입니다.");
        }

        messageSender.send(KafkaTopic.ITEM_PURCHASE_PRICE, itemSizePrice);
    }

    public List<Deal> findByUserId() {
        Long userId = SessionUtil.getLoginUserId();
        List<Deal> deals = dealMapper.findByUserId(userId);

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
        deal.setDealStatus(DealStatus.COMPLETION);
        Deal otherDeal = findById(deal.getOtherId());
        otherDeal.setDealStatus(DealStatus.COMPLETION);
        deal.setTradingDayToNow();
        otherDeal.setTradingDayToNow();
        update(deal);
        update(otherDeal);
        messageSender.send(KafkaTopic.DEAL, deal);
    }

    @Transactional
    public void cancelDeal(Long id) {
        Deal deal = findById(id);
        deal.setDealStatus(DealStatus.CANCEL);
        update(deal);
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

    public List<MarketPriceDto> findMarketPriceInGraph(Long itemId, DealPeriod period,
        String size) {
        LocalDate fromTradingDay = getPeriod(period);
        return dealMapper.findMarketPricesInGraph(itemId, fromTradingDay, size);
    }

    public List<MarketPriceDto> findMarketPrices(Long itemId, String size) {
        return dealMapper.findMarketPrices(itemId, size);
    }

    public List<BiddingPriceDto> findBiddingPrices(Long itemId, String size,
        DealType dealType) {
        return dealMapper.findBiddingPrices(itemId, size, dealType);
    }

    public Map<DealStatus, Integer> findHistoryCount(DealType dealType) {
        Long userId = SessionUtil.getLoginUserId();
        List<DealHistoryCountDto> historyCountDtos = dealMapper.findHistoryCount(userId,
            dealType);
        Map<DealStatus, Integer> historyCounts = new HashMap<>();
        for (DealHistoryCountDto historyCountDto : historyCountDtos) {
            historyCounts.put(historyCountDto.getDealStatus(), historyCountDto.getCount());
        }
        return historyCounts;
    }

    public List<DealHistoryDto> findPurchaseHistories(DealStatus dealStatus) {
        Long userId = SessionUtil.getLoginUserId();
        return dealMapper.findPurchaseHistories(userId, dealStatus);
    }

    public List<DealHistoryDto> findSaleHistories(DealStatus dealStatus) {
        Long userId = SessionUtil.getLoginUserId();
        return dealMapper.findSaleHistories(userId, dealStatus);
    }

    private LocalDate getPeriod(DealPeriod period) {
        if (period == DealPeriod.ONE_YEAR) {
            return LocalDate.now().minusYears(1);
        }
        if (period == DealPeriod.SIX_MONTH) {
            return LocalDate.now().minusMonths(6);
        }
        if (period == DealPeriod.THREE_MONTH) {
            return LocalDate.now().minusMonths(3);
        }
        if (period == DealPeriod.ONE_MONTH) {
            return LocalDate.now().minusMonths(1);
        }
        return null;
    }

    private void immediateSale(Deal saleDeal) {
        Deal purchaseDeal = findImmediatePurchaseDeal(saleDeal);

        String lockName = generateLockName(purchaseDeal);
        RLock rLock = redissonClient.getLock(lockName);
        try {
            if (!rLock.tryLock(0, TimeUnit.MILLISECONDS)) {
                throw new NoDataFoundException();
            }
            setDealTypeToProgress(saleDeal, purchaseDeal);
            linkDeals(saleDeal, purchaseDeal);
            updateDeals(saleDeal, purchaseDeal);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (rLock != null && rLock.isLocked()) {
                rLock.unlock();
            }
        }
    }

    private void immediatePurchase(Deal purchaseDeal) {
        Deal saleDeal = findImmediateSaleDeal(purchaseDeal);

        String lockName = generateLockName(saleDeal);
        RLock rLock = redissonClient.getLock(lockName);
        try {
            if (!rLock.tryLock(0, TimeUnit.MILLISECONDS)) {
                throw new NoDataFoundException();
            }
            setDealTypeToProgress(purchaseDeal, saleDeal);
            linkDeals(purchaseDeal, saleDeal);
            updateDeals(purchaseDeal, saleDeal);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            unlock(rLock);
        }
    }

    private void unlock(RLock rLock) {
        if (rLock != null && rLock.isLocked()) {
            rLock.unlock();
        }
    }

    private void updateDeals(Deal deal1, Deal deal2) {
        dealMapper.save(deal1);
        dealMapper.update(deal2);
    }

    private static void linkDeals(Deal saleDeal, Deal purchaseDeal) {
        saleDeal.setOtherId(purchaseDeal.getId());
        purchaseDeal.setOtherId(saleDeal.getId());
    }

    private static void setDealTypeToProgress(Deal saleDeal, Deal purchaseDeal) {
        saleDeal.setDealStatus(DealStatus.PROGRESS);
        purchaseDeal.setDealStatus(DealStatus.PROGRESS);
    }

    private String generateLockName(Deal deal) {
        return deal.getClass().getName() + deal.getId().toString();
    }

    private void bidSale(Deal deal) {
        deal.setDealStatus(DealStatus.BIDDING);
        dealMapper.save(deal);
    }

    private void bidPurchase(Deal deal) {
        deal.setDealStatus(DealStatus.BIDDING);
        dealMapper.save(deal);
    }

    private void updatePrice(Deal deal, ItemSizePrice itemSizePrice) {
        String lockName = generateLockName(itemSizePrice);
        RLock rLock = getLock(lockName);

        try {
            if (!tryLock(rLock)) {
                return;
            }
            updatePriceBasedOnDealStatus(deal, itemSizePrice);
        } catch (InterruptedException e) {
            throw new RuntimeException("Error while trying to acquire lock: " + e.getMessage(), e);
        } finally {
            unlock(rLock);
        }
    }

    private String generateLockName(ItemSizePrice itemSizePrice) {
        return itemSizePrice.getClass().getName() + itemSizePrice.getId().toString();
    }

    private RLock getLock(String lockName) {
        return redissonClient.getLock(lockName);
    }

    private boolean tryLock(RLock rLock) throws InterruptedException {
        return rLock.tryLock(1, 3, TimeUnit.MINUTES);
    }

    private void updatePriceBasedOnDealStatus(Deal deal, ItemSizePrice itemSizePrice) {
        if (deal.getDealStatus() == DealStatus.BIDDING) {
            updatePriceForBiddingDeal(deal, itemSizePrice);
        } else if (deal.getDealStatus() == DealStatus.PROGRESS) {
            updatePriceForProgressDeal(deal, itemSizePrice);
        }
        itemSizePriceService.update(itemSizePrice);
    }

    private void updatePriceForBiddingDeal(Deal deal, ItemSizePrice itemSizePrice) {
        if (deal.getDealType() == DealType.PURCHASE) {
            updatePriceForPurchaseDeal(deal, itemSizePrice);
        } else if (deal.getDealType() == DealType.SALE) {
            updatePriceForSaleDeal(deal, itemSizePrice);
        }
    }

    private void updatePriceForPurchaseDeal(Deal deal, ItemSizePrice itemSizePrice) {
        if (itemSizePrice.getImmediateSalePrice() == null
            || deal.getPrice() > itemSizePrice.getImmediateSalePrice()) {
            itemSizePrice.setImmediateSalePrice(deal.getPrice());
        }
    }

    private void updatePriceForSaleDeal(Deal deal, ItemSizePrice itemSizePrice) {
        if (itemSizePrice.getImmediatePurchasePrice() == null
            || deal.getPrice() < itemSizePrice.getImmediatePurchasePrice()) {
            itemSizePrice.setImmediatePurchasePrice(deal.getPrice());
        }
    }

    private void updatePriceForProgressDeal(Deal deal, ItemSizePrice itemSizePrice) {
        Integer highestPurchasePrice = dealMapper.findHighestPurchasePriceByItemIdAndSize(
            itemSizePrice.getItemId(), itemSizePrice.getSize());
        Integer lowestSalePrice = dealMapper.findLowestSalePriceByItemIdAndSize(
            itemSizePrice.getItemId(), itemSizePrice.getSize());
        itemSizePrice.changePrice(highestPurchasePrice, lowestSalePrice);
    }

    private Deal findImmediatePurchaseDeal(Deal deal) {
        Deal purchaseHistory = dealMapper.findBuyNowDealByItemIdAndSizeAndPrice(
            deal.getItem().getId(), deal.getSize(), deal.getPrice());
        if (purchaseHistory == null) {
            throw new NoDataFoundException();
        }
        purchaseHistory.setItem(itemService.findOne(deal.getItem().getId()));
        return purchaseHistory;
    }

    private Deal findImmediateSaleDeal(Deal deal) {
        Deal saleHistory = dealMapper.findSellNowDealByItemIdAndSizeAndPrice(deal.getItem().getId(),
            deal.getSize(), deal.getPrice());
        if (saleHistory == null) {
            throw new NoDataFoundException();
        }
        saleHistory.setItem(itemService.findOne(deal.getItem().getId()));
        return saleHistory;
    }

    private void handleImmediatePurchase(Deal deal, ItemSizePrice itemSizePrice) {
        if (itemSizePrice.getImmediateSalePrice() == null
            || deal.getPrice() != itemSizePrice.getImmediateSalePrice()) {
            throw new NoMatchDealStatusException("즉시 구매 진행중 에러 발생, 다시 시도해주세요.");
        }
        if (deal.getPrice() == itemSizePrice.getImmediatePurchasePrice()) {
            immediatePurchase(deal);
            updatePrice(deal, itemSizePrice);
        }
    }

    private void handleBiddingPurchase(Deal deal, ItemSizePrice itemSizePrice) {
        if (itemSizePrice.getImmediatePurchasePrice() == null
            || deal.getPrice() < itemSizePrice.getImmediatePurchasePrice()) {
            bidPurchase(deal);
            updatePrice(deal, itemSizePrice);
        }
        if (deal.getPrice() >= itemSizePrice.getImmediateSalePrice()) {
            throw new NoRequestHigherPriceThenImmediatePurchaseException();
        }
    }

    private void handleImmediateSale(Deal deal, ItemSizePrice itemSizePrice) {
        if (itemSizePrice.getImmediateSalePrice() == null
            || deal.getPrice() != itemSizePrice.getImmediateSalePrice()) {
            throw new NoMatchDealStatusException("즉시 판매 진행 중 에러 발생, 다시 시도해주세요.");
        }
        immediateSale(deal);
        updatePrice(deal, itemSizePrice);
    }
}
