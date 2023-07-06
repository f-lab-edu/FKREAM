package com.flab.fkream.search;

import com.flab.fkream.item.Item;
import com.flab.fkream.item.ItemService;
import com.flab.fkream.itemCategory.ItemCategoryService;
import com.flab.fkream.itemImg.ItemImg;
import com.flab.fkream.itemImg.ItemImgService;
import com.flab.fkream.kafka.KafkaMessageSender;
import com.flab.fkream.kafka.KafkaTopic;
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

    private final ItemService itemService;
    private final ItemImgService itemImgService;
    private final Trie trie;
    private final KafkaMessageSender messageSender;


    private static final String CATEGORY_ERROR_MESSAGE = "잘못된 카테고리를 입력하셨습니다.";

    public List<SearchItemDto> search(SearchCriteria searchCriteria) {
        if (searchCriteria == null) {
            return searchMapper.searchAll();
        }
        validateCriteria(searchCriteria);
        if (searchCriteria.getContext() != null && !searchCriteria.getContext().isEmpty()) {
            messageSender.send(KafkaTopic.SEARCH_LOG, searchCriteria.getContext());
        }
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
        List<Item> results = trie.search(word);
        List<AutoCompletedItemDto> itemDtos = new ArrayList<>();
        for (Item item : results) {
            ItemImg itemImg = itemImgService.findRepresentImageByItemID(item.getId());
            itemDtos.add(AutoCompletedItemDto.of(item, itemImg));
        }
        return itemDtos;
    }

    private void validateCriteria(SearchCriteria searchCriteria) {
        searchCriteria.validatePrice();
        if (!itemCategoryService.isValidCategoryId(searchCriteria.getCategoryId())) {
            throw new IllegalArgumentException(CATEGORY_ERROR_MESSAGE);
        }
    }

    public void initTrie() {
        List<Item> all = itemService.findAll();
        for (Item item : all) {
            trie.insert(item);
        }
    }
}
