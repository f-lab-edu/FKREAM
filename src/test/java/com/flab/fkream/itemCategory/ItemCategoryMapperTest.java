package com.flab.fkream.itemCategory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles({"test"})
class ItemCategoryMapperTest {

    @Autowired
    ItemCategoryMapper itemCategoryMapper;

    ItemCategory parentCategory = ItemCategory.builder().name("신발").build();
    ItemCategory childCategory;


    @Test
    void add() {
        assertThat(itemCategoryMapper.save(parentCategory)).isEqualTo(1);
    }

    @Test
    void findParentCategory() {
        System.out.println("parentCategory = " + parentCategory);
        itemCategoryMapper.save(parentCategory);
        System.out.println("p = " + parentCategory);
        List<ItemCategory> parentCategory1 = itemCategoryMapper.findParentCategory();
        System.out.println("parentCategory1 = " + parentCategory1);
        assertThat(itemCategoryMapper.findParentCategory()).contains(parentCategory);
    }

    @Test
    void findChildCategory() {
        itemCategoryMapper.save(parentCategory);
        childCategory = ItemCategory.builder().name("스니커즈").parentId(parentCategory.getId())
            .build();
        itemCategoryMapper.save(childCategory);
        assertThat(itemCategoryMapper.findChildCategory(parentCategory.getId())).contains(
            childCategory);
    }

    @Test
    void delete() {
        itemCategoryMapper.save(parentCategory);
        assertThat(itemCategoryMapper.delete(parentCategory.getId())).isEqualTo(1);
    }

    @Test
    void findById() {
        itemCategoryMapper.save(parentCategory);
        assertThat(itemCategoryMapper.findById(parentCategory.getId())).isEqualTo(parentCategory);
    }
}