package com.flab.fkream.brand;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BrandMapper {
	int save(Brand brand);

	Brand findOne(Long id);

	List<Brand> findAll();

	int update(Brand brandInfo);

	int delete(Long id);
}
