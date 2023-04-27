package com.flab.fkream.listing;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ListingTest {

    @Autowired
    ListingMapper listingMapper;
    @Autowired
    CacheManager cacheManager;

    LocalDate now = LocalDate.now();
    LocalDate lastMonth = now.minusMonths(1);

    LocalDate twoMonthAgo = lastMonth.minusMonths(1);


    @Test
    void listingCacheTest() {
        listingMapper.generateRecommendedItemsForMen(1);
        listingMapper.generateRecommendedItemsForWomen(1);
        listingMapper.generateItemsBelowReleasedPrice(1);
        listingMapper.generatePopularLuxuryItems(1);
        listingMapper.generateMostPopularItems();
        listingMapper.generatePopularSneakers(1);

        Cache listing = cacheManager.getCache("Listing");
        assertThat(listing.get("generateRecommendedItemsForMen")).isNotNull();
        assertThat(listing.get("generateRecommendedItemsForWomen")).isNotNull();
        assertThat(listing.get("generateItemsBelowReleasedPrice")).isNotNull();
        assertThat(listing.get("generatePopularLuxuryItems")).isNotNull();
        assertThat(listing.get("generateMostPopularItems")).isNotNull();
        assertThat(listing.get("generatePopularSneakers")).isNotNull();
    }
}
