package com.flab.fkream.itemSizePrice;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface ItemSizePriceMapper {
	int save(ItemSizePrice itemSizePrice);

	ItemSizePrice findOne(Long id);

	@Transactional(readOnly = true)
	List<ItemSizePrice> findAllByItemId(Long itemId);
	@Transactional(readOnly = true)
	ItemSizePrice findByItemIdAndSize(Long itemId, String size);

	int update(ItemSizePrice itemSizePrice);

	int delete(Long id);
}
