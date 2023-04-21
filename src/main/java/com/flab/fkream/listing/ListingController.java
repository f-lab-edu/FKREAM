package com.flab.fkream.listing;

import com.flab.fkream.search.SearchItemDto;
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

    @GetMapping("/for-man")
    public List<SearchItemDto>  generateRecommendedItemsListForMen() {
        return listingService.generateRecommendedItemsListForMen();
    }

    @GetMapping("/for-women")
    public List<SearchItemDto> generateRecommendedItemsListForWomen() {
        return listingService.generateRecommendedItemsListForWomen();
    }

    @GetMapping("/for-item-below-released-price")
    public List<SearchItemDto> generateItemsBelowReleasedPrice() {
        return listingService.generateItemsBelowReleasedPrice();
    }

    @GetMapping("/popular-luxury")
    public List<SearchItemDto> generatePopularLuxuryItems() {
        return listingService.generatePopularLuxuryItems();
    }

    @GetMapping("/most-popular")
    public List<SearchItemDto> generateMostPopularItems() {
        return listingService.generateMostPopularItems();
    }

    @GetMapping("popular-sneakers")
    public List<SearchItemDto> generatePopularSneakers() {
        return listingService.generatePopularSneakers();
    }

}
