package com.flab.fkream.interestItemCount;

import com.flab.fkream.error.exception.NoDataFoundException;
import com.flab.fkream.item.Item;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.itemSizePrice.ItemSizePriceService;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class InterestItemCountService {

    private final InterestItemCountRepository interestItemCountRepository;
    private final ItemSizePriceService itemSizePriceService;
    private final RedissonClient redissonClient;

    public void save(Item item) {
        interestItemCountRepository.save(new InterestItemCount(item.getId(), 0));
    }

    public InterestItemCount findByItemId(Long itemId) {
        InterestItemCount interestItemCount = interestItemCountRepository.findByItemId(itemId);
        if (interestItemCount == null) {
            throw new NoDataFoundException();
        }
        return interestItemCount;
    }

    public void increaseCount(Long itemId) {
        final String lockName =
            this.getClass().toString() + itemId.toString();
        RLock rLock = redissonClient.getLock(lockName);

        try {
            if (!rLock.tryLock(1, 3, TimeUnit.MINUTES)) {
                return;
            }
            InterestItemCount interestItemCount = findByItemId(itemId);
            interestItemCount.increaseCount();
            interestItemCountRepository.save(interestItemCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (rLock != null && rLock.isLocked()) {
                rLock.unlock();
            }
        }
    }

    public void decreaseCount(Long itemId) {
        final String lockName =
            this.getClass().toString() + itemId.toString();
        RLock rLock = redissonClient.getLock(lockName);

        try {
            if (!rLock.tryLock(1, 3, TimeUnit.MINUTES)) {
                return;
            }
            InterestItemCount interestItemCount = findByItemId(itemId);
            interestItemCount.decreaseCount();
            interestItemCountRepository.save(interestItemCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (rLock != null && rLock.isLocked()) {
                rLock.unlock();
            }
        }
    }
}
