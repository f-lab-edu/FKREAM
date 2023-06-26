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
import org.redisson.Redisson;
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

        if (deal.getDealType() != DealType.SALE) {
            throw new NoMatchDealTypeException();
        }

        deal.setCreatedAtToNow();

        ItemSizePrice itemSizePrice = itemSizePriceService.findByItemIdAndSize(
            deal.getItem().getId(), deal.getSize());

        if (deal.getStatus() == Status.BIDDING) {
            if (itemSizePrice.getHighestPurchasePrice() == null
                || deal.getPrice() > itemSizePrice.getHighestPurchasePrice()) {
                bidSale(deal);
                updatePrice(deal, itemSizePrice);
                return;
            }
            if (deal.getPrice() <= itemSizePrice.getHighestPurchasePrice()) {
                throw new NoRequestLowerPriceThenImmediateSaleException();
            }
        }

        if (deal.getStatus() == Status.PROGRESS) {
            if (itemSizePrice.getHighestPurchasePrice() == null
                || deal.getPrice() != itemSizePrice.getHighestPurchasePrice()) {
                throw new NoMatchDealStatusException("즉시 판매 진행 중 에러 발생, 다시 시도해주세요.");
            }
            if (deal.getPrice() == itemSizePrice.getHighestPurchasePrice()) {
                immediateSale(deal);
                updatePrice(deal, itemSizePrice);
                return;
            }
        }
    }

    @Transactional
    public void purchase(Deal deal) {

        if (deal.getDealType() != DealType.PURCHASE) {
            throw new NoMatchDealTypeException();
        }

        deal.setCreatedAtToNow();

        ItemSizePrice itemSizePrice = itemSizePriceService.findByItemIdAndSize(
            deal.getItem().getId(), deal.getSize());

        if (deal.getStatus() == Status.BIDDING) {
            if (itemSizePrice.getLowestSellingPrice() == null
                || deal.getPrice() < itemSizePrice.getLowestSellingPrice()) {
                bidPurchase(deal);
                updatePrice(deal, itemSizePrice);
                return;
            }
            if (deal.getPrice() >= itemSizePrice.getHighestPurchasePrice()) {
                throw new NoRequestHigherPriceThenImmediatePurchaseException();
            }
        }

        if (deal.getStatus() == Status.PROGRESS) {
            if (itemSizePrice.getHighestPurchasePrice() == null
                || deal.getPrice() != itemSizePrice.getHighestPurchasePrice()) {
                throw new NoMatchDealStatusException("즉시 구매 진행중 에러 발생, 다시 시도해주세요.");
            }
            if (deal.getPrice() == itemSizePrice.getLowestSellingPrice()) {
                immediatePurchase(deal);
                updatePrice(deal, itemSizePrice);
            }
        }
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
        deal.setTradingDayToNow();
        otherDeal.setTradingDayToNow();
        update(deal);
        update(otherDeal);
        messageSender.send(KafkaTopic.complete_deal_price.toString(), new CompleteDealPriceDto(deal.getItem().getId(), deal.getPrice()));
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

    public Map<Status, Integer> findHistoryCount(DealType dealType) {
        Long userId = SessionUtil.getLoginUserId();
        List<DealHistoryCountDto> historyCountDtos = dealMapper.findHistoryCount(userId,
            dealType);
        Map<Status, Integer> historyCounts = new HashMap<>();
        for (DealHistoryCountDto historyCountDto : historyCountDtos) {
            historyCounts.put(historyCountDto.getStatus(), historyCountDto.getCount());
        }
        return historyCounts;
    }

    public List<DealHistoryDto> findPurchaseHistories(Status status) {
        Long userId = SessionUtil.getLoginUserId();
        return dealMapper.findPurchaseHistories(userId, status);
    }

    public List<DealHistoryDto> findSaleHistories(Status status) {
        Long userId = SessionUtil.getLoginUserId();
        return dealMapper.findSaleHistories(userId, status);
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

    public void immediateSale(Deal saleDeal) {
        Deal purchaseDeal = findBuyNowDeal(saleDeal);
        String lockName = purchaseDeal.getClass().getName() + purchaseDeal.getId().toString();
        RLock rLock = redissonClient.getLock(lockName);
        try {
            if (!rLock.tryLock(0, TimeUnit.MILLISECONDS)) {
                log.info("락 획득 실패");
                throw new NoDataFoundException();
            }
            log.info("락 획득 성공");
            purchaseDeal.setStatus(Status.PROGRESS);
            saleDeal.setOtherId(purchaseDeal.getId());
            dealMapper.save(saleDeal);
            purchaseDeal.setOtherId(saleDeal.getId());
            dealMapper.update(purchaseDeal);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new  RuntimeException(e);
        } finally {
            if (rLock != null && rLock.isLocked()) {
                rLock.unlock();
            }
        }
    }

    private void immediatePurchase(Deal purchaseDeal) {
        Deal saleDeal = findSellNowDeal(purchaseDeal);
        final String lockName =
            saleDeal.getClass().getName() + saleDeal.getId().toString();
        RLock rLock = redissonClient.getLock(lockName);
        try {
            if (!rLock.tryLock(0, TimeUnit.MILLISECONDS)) {
                throw new NoDataFoundException();
            }
            saleDeal.setStatus(Status.PROGRESS);
            purchaseDeal.setOtherId(saleDeal.getId());
            dealMapper.save(purchaseDeal);
            saleDeal.setOtherId(purchaseDeal.getId());
            dealMapper.update(saleDeal);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (rLock != null && rLock.isLocked()) {
                rLock.unlock();
            }
        }
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
        final String lockName =
            itemSizePrice.getClass().getName() + itemSizePrice.getId().toString();
        RLock rLock = redissonClient.getLock(lockName);

        try {
            if (!rLock.tryLock(1, 3, TimeUnit.MINUTES)) {
                return;
            }

            if (deal.getStatus() == Status.BIDDING) {
                if (deal.getDealType() == DealType.PURCHASE) {
                    if (itemSizePrice.getHighestPurchasePrice() == null
                        || deal.getPrice() > itemSizePrice.getHighestPurchasePrice()) {
                        itemSizePrice.setHighestPurchasePrice(deal.getPrice());
                    }
                }
                if (deal.getDealType() == DealType.SALE) {
                    if (itemSizePrice.getLowestSellingPrice() == null
                        || deal.getPrice() < itemSizePrice.getLowestSellingPrice()) {
                        itemSizePrice.setLowestSellingPrice(deal.getPrice());
                    }
                }
            }
            if (deal.getStatus() == Status.PROGRESS) {
                Integer highestPurchasePrice = dealMapper.findHighestPurchasePriceByItemIdAndSize(
                    itemSizePrice.getItemId(), itemSizePrice.getSize());
                Integer lowestSalePrice = dealMapper.findLowestSalePriceByItemIdAndSize(
                    itemSizePrice.getItemId(), itemSizePrice.getSize());
                itemSizePrice.changePrice(highestPurchasePrice, lowestSalePrice);
            }
            itemSizePriceService.update(itemSizePrice);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (rLock != null && rLock.isLocked()) {
                rLock.unlock();
            }
        }
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
