package com.flab.fkream.search;

import com.flab.fkream.itemCategory.ItemCategoryService;
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

    public List<SearchItemDto> search(SearchCriteria searchCriteria) {
        if (searchCriteria == null) {
            return searchMapper.searchAll();
        }
        validateCriteria(searchCriteria);
        return searchMapper.searchByCriteria(searchCriteria);
    }

    public int findCount(SearchCriteria searchCriteria) {
        if (searchCriteria == null) {
            return searchMapper.findAllCount();
        }
        validateCriteria(searchCriteria);
        return searchMapper.findCountByCriteria(searchCriteria);
    }

    public List<AutoCompletedItemDto> autoComplete(String word) {
        if (word.isBlank()) {
            return List.of();
        }
        List<String> result = trie.search(word);
        return searchMapper.autoComplete(result);
    }

    private void validateCriteria(SearchCriteria searchCriteria) {
        searchCriteria.validatePrice();
        if (!itemCategoryService.isValidCategoryId(searchCriteria.getCategoryId())) {
            throw new IllegalArgumentException(CATEGORY_ERROR_MESSAGE);
        }
    }
}
