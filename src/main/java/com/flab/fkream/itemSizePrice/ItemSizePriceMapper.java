package com.flab.fkream.itemSizePrice;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemSizePriceMapper {
	int save(ItemSizePrice itemSizePrice);

	ItemSizePrice findOne(Long id);

	List<ItemSizePrice> findAllByItemId(Long itemId);

	ItemSizePrice findByItemIdAndSize(Long itemId, String size);

	int update(ItemSizePrice itemSizePrice);

	int delete(Long id);
}
