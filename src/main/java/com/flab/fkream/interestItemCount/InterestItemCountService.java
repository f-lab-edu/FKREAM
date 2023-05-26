package com.flab.fkream.interestItemCount;

import com.flab.fkream.deal.DealType;
import com.flab.fkream.deal.Status;
import com.flab.fkream.error.exception.NoDataFoundException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class InterestItemCountService {

    private final InterestItemCountRepository interestItemCountRepository;
    private final RedissonClient redissonClient;

    public void save(InterestItemCount interestItemCount) {
        InterestItemCount insert = interestItemCountRepository.insert(interestItemCount);
        log.info("{}", insert.toString());
    }

    public List<InterestItemCount> findAll() {
        return interestItemCountRepository.findAll();
    }

    public InterestItemCount findByItemId(Long itemId) {
        InterestItemCount interestItemCount = interestItemCountRepository.findByItemId(itemId);
        if (interestItemCount == null) {
            throw new NoDataFoundException();
        }
        return interestItemCount;
    }

    public void increaseCount(InterestItemCount interestItemCountInfo) {
        final String lockName =
            interestItemCountInfo.getClass().getName() + interestItemCountInfo.getId().toString();
        RLock rLock = redissonClient.getLock(lockName);

        try {
            if (!rLock.tryLock(1, 3, TimeUnit.MINUTES)) {
                return;
            }
            InterestItemCount interestItemCount = findByItemId(interestItemCountInfo.getItemId());
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
}
