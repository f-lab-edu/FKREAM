package com.flab.fkream.item;

import com.flab.fkream.brand.Brand;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Mapper
public interface ItemMapper {

    int save(Item item);

    //@Cacheable(cacheNames = "Item", key = "#p0")
    Item findOne(Long id);

    List<Item> findAll();

    int update(Item itemInfo);

    int delete(Long id);

    List<Item> findByBrand(Brand brand);
}
