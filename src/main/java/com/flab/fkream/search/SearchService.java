package com.flab.fkream.search;

import com.flab.fkream.itemCategory.ItemCategory;
import com.flab.fkream.itemCategory.ItemCategoryService;
import com.flab.fkream.itemSizePrice.ItemSizePriceService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class SearchService {

    private final SearchMapper searchMapper;
    private final ItemCategoryService itemCategoryService;
    private final Trie trie;

    private static final String CATEGORY_ERROR_MESSAGE = "잘못된 카테고리를 입력하셨습니다.";

    public List<SearchItemDto> search(String context, Long... categoryId) {
        if (categoryId == null || categoryId.length == 0) {
            return searchMapper.search(context);
        }
        if (itemCategoryService.isValidCategoryId(categoryId)) {
            return searchMapper.searchByCategory(context, categoryId);
        }
        throw new IllegalArgumentException(CATEGORY_ERROR_MESSAGE);
    }

    public int findCount(String context) {
        return searchMapper.findCount(context);
    }

    public List<AutoCompletedItemDto> autoComplete(String word) {
        if (word.isBlank()) {
            return List.of();
        }
        List<String> result = trie.search(word);
        return searchMapper.autoComplete(result);
    }
}
