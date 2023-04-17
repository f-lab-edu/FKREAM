package com.flab.fkream.itemCategory;

import java.util.List;
import lombok.Data;

@Data
public class ItemCategoryDto {

    private Long id;
    private String name;
    private List<ItemCategory> detailedCategories;

    public ItemCategoryDto(ItemCategory itemCategory, List<ItemCategory> detailedCategories) {
        this.id = itemCategory.getId();
        this.name = itemCategory.getName();
        this.detailedCategories = detailedCategories;
    }
}
