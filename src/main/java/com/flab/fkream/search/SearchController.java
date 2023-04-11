package com.flab.fkream.search;

import com.flab.fkream.itemImg.ItemImg;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/searches")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/{context}")
    public List<ItemImg> searchItem(@PathVariable String context){
        return searchService.search(context);
    }


}
