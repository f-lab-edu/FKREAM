package com.flab.fkream.brand;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BrandMapper {
	String insert = "INSERT INTO BRAND(id, brand_name, luxury) VALUES (#{brand.id}, #{brand.brandName}, #{brand.luxury})";

	@Insert(insert)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(@Param("brand") Brand brand);

	@Select("SELECT * FROM BRAND")
	@Results(id = "brandMapper", value = {
		@Result(property = "brandName", column = "brand_name")
	})
	List<Brand> getAll();

	@Select("SELECT * FROM BRAND WHERE brand_name = #{brandName}")
	Brand getByBrandName(@Param("brandName") String brandName);
}
