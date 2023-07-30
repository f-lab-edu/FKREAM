package com.flab.fkream.interestedItem;

import com.flab.fkream.interestItemCount.InterestItemCountService;
import com.flab.fkream.mapper.InterestedItemMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class InterestedItemService {

    private final InterestedItemMapper interestedItemMapper;
    private final InterestItemCountService interestItemCountService;

    public void save(InterestedItem interestedItem) {
        InterestedItem foundInterestedItem = interestedItemMapper.findByUserIdAndItemSizePriceId(
            interestedItem.getUserId(),
            interestedItem.getItemSizePriceId());

        if (foundInterestedItem != null) {
            throw new IllegalArgumentException();
        }

        interestedItemMapper.save(interestedItem);
    }

    public List<InterestedItem> findAllByUserId(Long userId) {
        return interestedItemMapper.findAllByUserId(userId);
    }

    public void delete(Long userId, Long itemSizePriceId) {
        InterestedItem foundInterestedItem = interestedItemMapper.findByUserIdAndItemSizePriceId(
            userId, itemSizePriceId);

        if (foundInterestedItem == null) {
            throw new IllegalArgumentException();
        }

        interestedItemMapper.deleteById(userId, itemSizePriceId);
        interestItemCountService.decreaseCount(foundInterestedItem.getItemSizePriceId());
    }

}
