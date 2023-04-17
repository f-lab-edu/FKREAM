package com.flab.fkream.itemCategory;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemCategoryServiceTest {

    @Mock
    ItemCategoryMapper itemCategoryMapper;

    @InjectMocks
    ItemCategoryService itemCategoryService;

    ItemCategory parentCategory = ItemCategory.builder().id(1L).name("신발").build();
    ItemCategory parentCategory2 = ItemCategory.builder().id(3L).name("상의").build();

    ItemCategory childCategory = ItemCategory.builder().id(2L).name("스니커즈").parentId(1L).build();

    @Test
    void addCategory() {
        given(itemCategoryMapper.save(parentCategory)).willReturn(1);
        itemCategoryService.addCategory(parentCategory);
        then(itemCategoryMapper).should().save(parentCategory);
    }

    @Test
    void findById() {
        given(itemCategoryMapper.findById(1L)).willReturn(parentCategory);
        assertThat(itemCategoryService.findById(1L)).isEqualTo(parentCategory);
    }

    @Test
    void findAllCategory() {
        given(itemCategoryMapper.findParentCategory()).willReturn(List.of(parentCategory));
        given(itemCategoryMapper.findChildCategory(1L)).willReturn(List.of(childCategory));
        assertThat(itemCategoryService.findAllCategory()).hasSize(1);
    }

    @Test
    void delete() {
        given(itemCategoryMapper.delete(1L)).willReturn(1);
        itemCategoryService.delete(1L);
        then(itemCategoryMapper).should().delete(1L);
    }

    @Test
    void isValidCategoryId() {
        given(itemCategoryMapper.findById(1L)).willReturn(parentCategory);
        given(itemCategoryMapper.findById(2L)).willReturn(parentCategory2);
        given(itemCategoryMapper.findById(3L)).willReturn(childCategory);
        assertThat(itemCategoryService.isValidCategoryId(new Long[]{1L, 2L, 3L})).isFalse();
        assertThat(itemCategoryService.isValidCategoryId(new Long[]{ 2L, 3L})).isTrue();

    }
}