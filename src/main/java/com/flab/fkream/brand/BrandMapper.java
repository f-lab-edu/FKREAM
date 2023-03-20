package com.flab.fkream.brand;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BrandMapper {
	Long insertBrand(Brand brandInfo);
}
