package com.flab.fkream.cache;


import static org.assertj.core.api.Assertions.*;

import com.flab.fkream.itemCategory.ItemCategory;
import com.flab.fkream.itemCategory.ItemCategoryMapper;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ItemCategoryTest {

    @Autowired
    CacheManager cacheManager;

    @SpyBean
    ItemCategoryMapper itemCategoryMapper;

    @Test
    void categoryTest() {
        ItemCategory categoryById = itemCategoryMapper.findById(1L);
        itemCategoryMapper.findById(2L);
        List<ItemCategory> parentCategories = itemCategoryMapper.findParentCategory();
        List<ItemCategory> childCategories = itemCategoryMapper.findChildCategory(2L);
        Cache itemCategory = cacheManager.getCache("ItemCategory");
        Cache itemCategoryAll = cacheManager.getCache("ItemCategoryAll");
        assertThat(itemCategoryAll.get("findParentCategory").get()).isEqualTo(parentCategories);
        assertThat(itemCategoryAll.get(2L).get()).isEqualTo(childCategories);
        assertThat(itemCategory.get(1L).get()).isEqualTo(categoryById);
        itemCategoryMapper.delete(1L);
        assertThat(itemCategory.get(1L)).isNull();
        assertThat(itemCategory.get(2L)).isNotNull();
        assertThat(itemCategoryAll.get("findParentCategory")).isNull();
        assertThat(itemCategoryAll.get(2L)).isNull();
    }
}
