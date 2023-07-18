package com.flab.fkream.listing;

import com.flab.fkream.search.dbSearch.SearchItemDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/listing")
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;

    @GetMapping
    public List<SearchItemDto> generateItems(ListingCriteria listingCriteria) {
        return listingService.generateItems(listingCriteria);
    }
}
