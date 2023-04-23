package com.flab.fkream.interestedItem;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InterestedItemMapper {

    int save(InterestedItem interestedItem);

    List<InterestedItem> findAllByUserId(Long userId);

    int deleteById(Long id);

}
