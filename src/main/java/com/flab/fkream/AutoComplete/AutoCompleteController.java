package com.flab.fkream.AutoComplete;

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

    private final AutoCompleteService autoCompleteService;

    @GetMapping("/auto-complete")
    public List<AutoCompletedItemDto> searchAutoCompletedItem(@RequestParam String context) {
        return autoCompleteService.autoComplete(context);
    }

    @GetMapping("/auto-complete-init")
    public void initTrie() {
        autoCompleteService.initTrie();
    }
}
