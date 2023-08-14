package com.flab.fkream.AutoComplete;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flab.fkream.item.Item;
import com.flab.fkream.item.ItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auto-complete")
@RequiredArgsConstructor
public class AutoCompleteController {

    private final ItemService autoCompleteService;

    @GetMapping("/auto-complete")
    public List<AutoCompletedItemDto> searchAutoCompletedItem(@RequestParam String context)
        throws JsonProcessingException {
        return autoCompleteService.autoComplete(context);
    }

    @GetMapping("/init")
    public void init() throws JsonProcessingException {
        autoCompleteService.initTrie();
    }

    @GetMapping("/test")
    public Item test() {
        return autoCompleteService.findOne(2L);
    }

    @GetMapping("/autoDB")
    public List<Item> searchItem(@RequestParam String context) {

        return autoCompleteService.autoCompleteByDB(context);
    }

    @GetMapping("/autoES")
    public List<Item> searchItemES(@RequestParam String context) {
        return autoCompleteService.autoCompleteByES(context);
    }

}
