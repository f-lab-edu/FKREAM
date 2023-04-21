package com.flab.fkream.listing;

import com.flab.fkream.search.SearchItemDto;
import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ListingMapper {

    List<SearchItemDto> generateRecommendedItemsListForMen(LocalDate now, LocalDate lastMonth);

    List<SearchItemDto> generateRecommendedItemsListForWomen(LocalDate now, LocalDate lastMonth);

    List<SearchItemDto> generateItemsBelowReleasedPrice(LocalDate now, LocalDate lastMonth);

    List<SearchItemDto> generatePopularLuxuryItems(LocalDate now, LocalDate lastMonth);

    List<SearchItemDto> generateMostPopularItems();

    List<SearchItemDto> generatePopularSneakers(LocalDate now, LocalDate lastMonth,
        LocalDate twoMonthAgo);
}
