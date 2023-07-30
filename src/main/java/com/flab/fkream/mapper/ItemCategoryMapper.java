package com.flab.fkream.mapper;

import com.flab.fkream.itemCategory.ItemCategory;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

@Mapper
public interface ItemCategoryMapper {

    int save(ItemCategory category);

    //@Cacheable(cacheNames = "ItemCategoryAll", key = "#root.methodName")
    List<ItemCategory> findParentCategory();

    //@Cacheable(cacheNames = "ItemCategoryAll", key = "#p0")
    List<ItemCategory> findChildCategory(Long parentCategoryId);

//    @Caching(evict = {
//        @CacheEvict(cacheNames = "ItemCategory", key = "#p0"),
//        @CacheEvict(cacheNames = "ItemCategoryAll", allEntries = true)
//    })
    int delete(Long id);

    //@Cacheable(cacheNames = "ItemCategory", key = "#p0")
    ItemCategory findById(Long id);
}
