package com.flab.fkream.listing;

import static org.assertj.core.api.Assertions.*;

import com.flab.fkream.brand.Brand;
import com.flab.fkream.brand.BrandMapper;
import com.flab.fkream.deal.DealMapper;
import com.flab.fkream.item.Item;
import com.flab.fkream.item.ItemGender;
import com.flab.fkream.item.ItemMapper;
import com.flab.fkream.itemCategory.ItemCategoryMapper;
import com.flab.fkream.search.SearchItemDto;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ListingMapperTest {

    @Autowired ListingMapper listingMapper;

    @Autowired
    DealMapper dealMapper;
    @Autowired
    ItemMapper itemMapper;
    @Autowired
    BrandMapper brandMapper;
    @Autowired
    ItemCategoryMapper itemCategoryMapper;

    LocalDate now = LocalDate.now();
    LocalDate lastMonth = now.minusMonths(1);
    LocalDate twoMonthAgo = lastMonth.minusMonths(1);

    @Test
    void generateRecommendedItemsListForMen() {
        List<SearchItemDto> searchItemDtos = listingMapper.generateRecommendedItemsForMen(1);
        SearchItemDto searchItemDto = searchItemDtos.get(0);
        Item item = itemMapper.findOne(searchItemDto.getItemId());
        assertThat(item.getGender()).isEqualTo(ItemGender.MALE);
        assertThat(searchItemDtos.get(0).getSortCriteria()).isGreaterThan(searchItemDtos.get(1).getSortCriteria());
    }

    @Test
    void generateRecommendedItemsListForWomen() {
        List<SearchItemDto> searchItemDtos = listingMapper.generateRecommendedItemsForWomen(1);
        SearchItemDto searchItemDto = searchItemDtos.get(0);
        Item item = itemMapper.findOne(searchItemDto.getItemId());
        assertThat(item.getGender()).isEqualTo(ItemGender.FEMALE);
        assertThat(searchItemDtos.get(0).getSortCriteria()).isGreaterThan(searchItemDtos.get(1).getSortCriteria());
    }

    @Test
    void generateItemsBelowReleasedPrice() {
        List<SearchItemDto> searchItemDtos = listingMapper.generateItemsBelowReleasedPrice(1);
        SearchItemDto searchItemDto = searchItemDtos.get(0);
        Item item = itemMapper.findOne(searchItemDto.getItemId());
        assertThat(searchItemDto.getPrice()).isLessThan(item.getReleasedPrice());
    }

    @Test
    void generatePopularLuxuryItems() {
        List<SearchItemDto> searchItemDtos = listingMapper.generatePopularLuxuryItems(1);
        SearchItemDto searchItemDto = searchItemDtos.get(0);
        Brand brand = brandMapper.findOne(searchItemDto.getBrandId());
        assertThat(brand.isLuxury()).isTrue();
    }

    @Test
    void generateMostPopularItems() {
        List<SearchItemDto> searchItemDtos = listingMapper.generateMostPopularItems();
        assertThat(searchItemDtos.get(0).getSortCriteria()).isGreaterThanOrEqualTo(searchItemDtos.get(1).getSortCriteria());
    }

    @Test
    void generatePopularSneakers() {
        List<SearchItemDto> searchItemDtos = listingMapper.generatePopularSneakers(1);
        SearchItemDto searchItemDto = searchItemDtos.get(0);
        Item item = itemMapper.findOne(searchItemDto.getItemId());
        String name = itemCategoryMapper.findById(item.getDetailedCategoryId()).getName();
        assertThat(name).isEqualTo("스니커즈");
    }
}