package com.flab.fkream.search;

import com.flab.fkream.itemSizePrice.ItemSizePriceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class SearchService {

    private final SearchMapper searchMapper;
    private final Trie trie;

    public List<SearchItemDto> search(String context) {

        List<SearchItemDto> searchItemDtos = searchMapper.search(context);
        return searchItemDtos;
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
