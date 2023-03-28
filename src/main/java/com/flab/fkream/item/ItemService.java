package com.flab.fkream.item;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ItemService {
	private final ItemMapper itemMapper;

	public Long addItem(Item itemInfo) {
		Long itemId = itemMapper.save(itemInfo);
		if (itemId == null) {
			log.error("insert item error! itemInfo : {}", itemInfo);
			throw new NullPointerException("insert item error!" + itemInfo);
		}
		return itemId;
	}

	public Item findOne(Long itemId) {
		return itemMapper.findOne(itemId);
	}

	public List<Item> findAll() {
		return itemMapper.findAll();
	}
}
