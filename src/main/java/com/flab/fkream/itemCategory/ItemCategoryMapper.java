package com.flab.fkream.itemCategory;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemCategoryMapper {

    int save(ItemCategory category);

    List<ItemCategory> findParentCategory();

    List<ItemCategory> findChildCategory(Long parentCategoryId);

    int delete(Long id);

    ItemCategory findById(Long id);
}
