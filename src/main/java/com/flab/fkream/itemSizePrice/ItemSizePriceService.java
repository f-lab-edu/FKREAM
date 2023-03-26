package com.flab.fkream.itemSizePrice;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ItemSizePriceService {
	private final ItemSizePriceMapper itemSizePriceMapper;

	@Transactional(rollbackFor = RuntimeException.class)
	public Long addItemSizePrice(ItemSizePrice itemSizePriceInfo) {
		Long itemSizePriceId = itemSizePriceMapper.save(itemSizePriceInfo);
		if (itemSizePriceId == null) {
			log.error("insert ItemSizePrice error! itemSizePriceInfo : {}", itemSizePriceInfo);
			throw new NullPointerException("insert itemSizePrice error!" + itemSizePriceInfo);
		}
		return itemSizePriceId;
	}

	public ItemSizePrice findOne(Long id) {
		return itemSizePriceMapper.findOne(id);
	}

	public List<ItemSizePrice> findAll() {
		return itemSizePriceMapper.findAll();
	}
}
