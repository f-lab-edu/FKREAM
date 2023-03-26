package com.flab.fkream.ownedItems;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OwnedItemMapper {
	Long save(OwnedItem ownedItem);

	OwnedItem findOne(Long id);

	List<OwnedItem> findAll();
}
