package com.flab.fkream.interestedItem;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InterestedItemMapper {
	Long save(InterestedItem interestedItem);

	InterestedItem findOne(Long id);

	List<InterestedItem> findAll();

}
