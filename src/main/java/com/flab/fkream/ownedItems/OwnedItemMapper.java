package com.flab.fkream.ownedItems;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OwnedItemMapper {

    int save(OwnedItem ownedItem);

    OwnedItem findOne(Long id);

    List<OwnedItem> findAllByUserId(Long userId);

    int update(OwnedItem ownedItem);

    int delete(Long id);
}
