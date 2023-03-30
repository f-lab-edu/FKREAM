package com.flab.fkream.itemImg;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemImgMapper {
	int save(ItemImg itemImg);

	List<ItemImg> findImagesByItemId(Long itemId);

	int delete(Long id);
}
