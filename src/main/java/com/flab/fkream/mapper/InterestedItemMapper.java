package com.flab.fkream.mapper;

import com.flab.fkream.interestedItem.InterestedItem;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InterestedItemMapper {

    int save(InterestedItem interestedItem);

    InterestedItem findByUserIdAndItemSizePriceId(Long userId, Long itemSizePriceId);

    List<InterestedItem> findAllByUserId(Long userId);

    int deleteById(@Param("userId") Long userId, @Param("itemSizePriceId") Long itemSizePriceId);

}
