package com.flab.fkream.itemImg;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemImgMapper {
	Long save(ItemImg itemImg);

	ItemImg findOne(Long id);

	List<ItemImg> findAll();
}
