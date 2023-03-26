package com.flab.fkream.ownedItems;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class OwnedItemService {
	private final OwnedItemMapper ownedItemMapper;

	@Transactional(rollbackFor = RuntimeException.class)
	public Long addOwnedItem(OwnedItem ownedItemInfo) {
		Long ownedItemId = ownedItemMapper.save(ownedItemInfo);
		if (ownedItemId == null) {
			log.error("insert ownedItem error! ownedInfo : {}", ownedItemInfo);
			throw new NullPointerException("insert ownedItem error!" + ownedItemInfo);
		}
		return ownedItemId;
	}

	public OwnedItem findOne(Long ownedItemId) {
		return ownedItemMapper.findOne(ownedItemId);
	}

	public List<OwnedItem> findAll() {
		return ownedItemMapper.findAll();
	}
}
