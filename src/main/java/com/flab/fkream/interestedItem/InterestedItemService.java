package com.flab.fkream.interestedItem;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class InterestedItemService {
	private final InterestedItemMapper interestedItemMapper;

	@Transactional(rollbackFor = RuntimeException.class)
	public Long addInterestedItem(InterestedItem interestedItemInfo) {
		Long interestedItemId = interestedItemMapper.save(interestedItemInfo);
		if (interestedItemId == null) {
			log.error("insert interestedItem error! interestedItemInfo : {}", interestedItemInfo);
			throw new NullPointerException("insert interestedItem error!" + interestedItemInfo);
		}
		return interestedItemId;
	}

	public InterestedItem findOne(Long interestedItemId) {
		return interestedItemMapper.findOne(interestedItemId);
	}

	public List<InterestedItem> findAll() {
		return interestedItemMapper.findAll();
	}
}
