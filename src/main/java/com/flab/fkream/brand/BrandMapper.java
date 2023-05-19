package com.flab.fkream.brand;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

@Mapper
public interface BrandMapper {

    int save(Brand brand);

    //@Cacheable(cacheNames = "Brand", key = "#p0")
    Brand findOne(Long id);

    List<Brand> findAll();

    //@CacheEvict(cacheNames = "Brand", key = "#p0.id")
    int update(Brand brandInfo);

    //@CacheEvict(cacheNames = "Brand", key = "#p0")
    int delete(Long id);

    Brand findByBrandName(String brandName);
}
