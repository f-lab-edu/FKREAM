package com.flab.fkream.itemCategory;

import com.flab.fkream.error.exception.NoDataFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ItemCategoryService {

    private final ItemCategoryMapper itemCategoryMapper;

    public void addCategory(ItemCategory category) {
        itemCategoryMapper.save(category);
    }

    public ItemCategory findById(Long id) {
        ItemCategory itemCategory = itemCategoryMapper.findById(id);
        if (itemCategory == null) {
            throw new NoDataFoundException();
        }
        return itemCategory;
    }

    public List<ItemCategoryDto> findAllCategory() {
        List<ItemCategoryDto> itemCategoryDtos = new ArrayList<>();
        List<ItemCategory> parentCategories = itemCategoryMapper.findParentCategory();

        for (ItemCategory parentCategory : parentCategories) {
            List<ItemCategory> childCategories = itemCategoryMapper.findChildCategory(
                parentCategory.getId());
            itemCategoryDtos.add(new ItemCategoryDto(parentCategory, childCategories));
        }
        return itemCategoryDtos;
    }

    public void delete(Long id) {
        itemCategoryMapper.delete(id);
    }

    /**
     * 부모 카테고리가 있으면 해당 부모의 자식 카테고리가 있으면 안된다.
     */
    public boolean isValidCategoryId(Long[] categoryId) {
        if (categoryId==null){
            return true;
        }
        List<Long> categories = List.of(categoryId);
        for (Long id : categories) {
            ItemCategory itemCategory = findById(id);
            if (itemCategory.getParentId() != null && categories.contains(
                itemCategory.getParentId())) {
                return false;
            }
        }
        return true;
    }
}
