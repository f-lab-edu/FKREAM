package com.flab.fkream.itemImg;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ItemImgService {
	private final ItemImgMapper itemImgMapper;

	@Transactional(rollbackFor = RuntimeException.class)
	public Long addItemImg(ItemImg itemImgInfo) {
		Long itemImgId = itemImgMapper.save(itemImgInfo);
		if (itemImgId == null) {
			log.error("insert ItemImg error! itemImgInfo : {}", itemImgInfo);
			throw new NullPointerException("insert itemImg error!" + itemImgInfo);
		}
		return itemImgId;
	}

	public ItemImg findOne(Long itemId) {
		return itemImgMapper.findOne(itemId);
	}

	public List<ItemImg> findAll() {
		return itemImgMapper.findAll();
	}
}
