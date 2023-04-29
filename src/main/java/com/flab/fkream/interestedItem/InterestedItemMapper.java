package com.flab.fkream.interestedItem;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InterestedItemMapper {

    int save(InterestedItem interestedItem);

    List<InterestedItem> findAllByUserId(Long userId);

    int deleteById(@Param("userId") Long userId, @Param("itemSizePriceId") Long itemSizePriceId);

    List<Long> findUserIdsByItemSizePriceID(Long itemSizePriceId);
}
