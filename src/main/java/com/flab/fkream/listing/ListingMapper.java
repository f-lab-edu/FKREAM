package com.flab.fkream.listing;

import com.flab.fkream.search.SearchItemDto;
import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;

@Mapper
public interface ListingMapper {


//    @Cacheable(cacheNames = "Listing", key = "#root.methodName" )
    List<SearchItemDto> generateRecommendedItemsForMen(int month);

//    @Cacheable(cacheNames = "Listing", key = "#root.methodName")
    List<SearchItemDto> generateRecommendedItemsForWomen(int month);


//    @Cacheable(cacheNames = "Listing", key = "#root.methodName")
    List<SearchItemDto> generateItemsBelowReleasedPrice(int month);


//    @Cacheable(cacheNames = "Listing", key = "#root.methodName")
    List<SearchItemDto> generatePopularLuxuryItems(int month);


//    @Cacheable(cacheNames = "Listing", key = "#root.methodName")
    List<SearchItemDto> generateMostPopularItems();


//    @Cacheable(cacheNames = "Listing", key = "#root.methodName")
    List<SearchItemDto> generatePopularSneakers(int month);
}
