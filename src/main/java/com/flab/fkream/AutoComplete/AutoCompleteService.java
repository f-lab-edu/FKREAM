package com.flab.fkream.AutoComplete;

import com.flab.fkream.error.exception.NoDataFoundException;
import com.flab.fkream.item.Item;
import com.flab.fkream.item.ItemService;
import com.flab.fkream.itemImg.ItemImg;
import com.flab.fkream.itemImg.ItemImgService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AutoCompleteService {
    private static final String REDIS_AUTO_COMPLETE_KEY = "AutoComplete";

    @Resource(name = "redisValueOperations")
    private ValueOperations<String, Object> valueOps;
    //private final ItemService itemService;
    private final ItemImgService itemImgService;

    public List<AutoCompletedItemDto> autoComplete(String word) {
        if (word.isBlank()) {
            return List.of();
        }
        Trie trie = (Trie)valueOps.get(REDIS_AUTO_COMPLETE_KEY);
        List<Item> results = trie.search(word);
        List<AutoCompletedItemDto> itemDtos = new ArrayList<>();
        for (Item item : results) {
            ItemImg itemImg = itemImgService.findRepresentImageByItemID(item.getId());
            itemDtos.add(AutoCompletedItemDto.of(item, itemImg));
        }
        return itemDtos;
    }

    public void addItem(Item item) {
        Trie trie = (Trie)valueOps.get(REDIS_AUTO_COMPLETE_KEY);
        if (trie == null) {
            throw new NoDataFoundException();
        }
        trie.insert(item);
        valueOps.set(REDIS_AUTO_COMPLETE_KEY, trie);
    }

    /*public void initTrie() {
        List<Item> all = itemService.findAll();
        Trie trie = new Trie();
        for (Item item : all) {
            trie.insert(item);
        }
        valueOps.set(REDIS_AUTO_COMPLETE_KEY, trie);
    }*/
}

