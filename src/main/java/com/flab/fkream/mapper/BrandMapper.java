package com.flab.fkream.mapper;

import com.flab.fkream.brand.Brand;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface BrandMapper {

    int save(Brand brand);

    //@Cacheable(cacheNames = "Brand", key = "#p0")
    Brand findOne(Long id);

    List<Brand> findAll();

    @CacheEvict(cacheNames = "Brand", key = "#p0.id")
    int update(Brand brandInfo);

    int delete(Long id);

    Brand findByBrandName(String brandName);
}
