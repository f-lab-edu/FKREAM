package com.flab.fkream.itemCategory;


import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class ItemCategoryController {

    private final ItemCategoryService itemCategoryService;


    @PostMapping("")
    public void addCategory(@Valid @RequestBody ItemCategory itemCategory) {
        itemCategoryService.addCategory(itemCategory);
    }

    @GetMapping("")
    public List<ItemCategoryDto> findAll() {
        return itemCategoryService.findAllCategory();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        itemCategoryService.delete(id);
    }
}
