package com.flab.fkream.mapper;

import com.flab.fkream.brand.Brand;
import com.flab.fkream.item.Item;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface ItemMapper {

    int save(Item item);

    @Cacheable(cacheNames = "Item", key = "#p0")
    Item findOne(Long id);

    List<Item> findAll();

    int update(Item itemInfo);

    int delete(Long id);

    List<Item> findByBrand(Brand brand);

    List<Item> findByPrefix(String content);
}
