package com.flab.fkream.listing;

import com.flab.fkream.search.SearchItemDto;
import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;

@Mapper
public interface ListingMapper {

    @Cacheable(cacheNames = "Listing", key = "#root.methodName")
    List<SearchItemDto> generateRecommendedItemsForMen(LocalDate now, LocalDate lastMonth);

    @Cacheable(cacheNames = "Listing", key = "#root.methodName")
    List<SearchItemDto> generateRecommendedItemsForWomen(LocalDate now, LocalDate lastMonth);

    @Cacheable(cacheNames = "Listing", key = "#root.methodName")
    List<SearchItemDto> generateItemsBelowReleasedPrice(LocalDate now, LocalDate lastMonth);

    @Cacheable(cacheNames = "Listing", key = "#root.methodName")
    List<SearchItemDto> generatePopularLuxuryItems(LocalDate now, LocalDate lastMonth);

    @Cacheable(cacheNames = "Listing", key = "#root.methodName")
    List<SearchItemDto> generateMostPopularItems();

    @Cacheable(cacheNames = "Listing", key = "#root.methodName")
    List<SearchItemDto> generatePopularSneakers(LocalDate now, LocalDate lastMonth,
        LocalDate twoMonthAgo);
}
