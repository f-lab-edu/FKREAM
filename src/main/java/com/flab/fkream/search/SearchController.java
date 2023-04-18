package com.flab.fkream.search;

import com.flab.fkream.itemImg.ItemImg;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("")
    public List<SearchItemDto> searchItem(
        @RequestParam(required = false) SearchCriteria searchCriteria) {
        return searchService.search(searchCriteria);
    }

    @GetMapping("/count")
    public int searchItemCount(@RequestParam(required = false) SearchCriteria searchCriteria) {
        return searchService.findCount(searchCriteria);
    }

    @GetMapping("/autoComplete")
    public List<AutoCompletedItemDto> searchAutoCompletedItem(@RequestParam String context) {
        return searchService.autoComplete(context);
    }
}
