package com.flab.fkream.brand;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BrandMapper {
	Long save(Brand brand);

	Brand findOne(Long id);

	List<Brand> findAll();
}
