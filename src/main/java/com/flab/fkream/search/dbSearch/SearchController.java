package com.flab.fkream.search.dbSearch;

import com.flab.fkream.resolver.QueryStringArgResolver;
import com.flab.fkream.search.SearchCriteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchServiceImpl searchService;
    @GetMapping("")
    public PageInfo<SearchItemDto> searchItem(
        @QueryStringArgResolver SearchCriteria criteria, @RequestParam int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return PageInfo.of(searchService.search(criteria));
    }

    @GetMapping("/count")
    public int searchItemCount(@RequestParam(required = false) SearchCriteria searchCriteria) {
        return searchService.findCount(searchCriteria);
    }


}
