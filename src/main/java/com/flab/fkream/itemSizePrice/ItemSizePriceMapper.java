package com.flab.fkream.itemSizePrice;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemSizePriceMapper {
	Long save(ItemSizePrice itemSizePrice);

	ItemSizePrice findOne(Long id);

	List<ItemSizePrice> findAll();

}
