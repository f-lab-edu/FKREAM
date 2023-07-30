package com.flab.fkream.search;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.fkream.mapper.BrandMapper;
import com.flab.fkream.mapper.DealMapper;
import com.flab.fkream.item.Item;
import com.flab.fkream.item.ItemGender;
import com.flab.fkream.mapper.ItemMapper;
import com.flab.fkream.mapper.ItemCategoryMapper;
import com.flab.fkream.mapper.ItemImgMapper;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.mapper.ItemSizePriceMapper;
import com.flab.fkream.search.dbSearch.SearchItemDto;
import com.flab.fkream.mapper.SearchMapper;
import com.flab.fkream.mapper.UserMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles({"test"})
class SearchMapperTest {

    @Autowired
    SearchMapper searchMapper;

    @Autowired
    ItemMapper itemMapper;
    @Autowired
    UserMapper userMapper;

    @Autowired
    DealMapper dealMapper;

    @Autowired
    ItemImgMapper itemImgMapper;

    @Autowired
    BrandMapper brandMapper;

    @Autowired
    ItemSizePriceMapper itemSizePriceMapper;

    @Autowired
    ItemCategoryMapper itemCategoryMapper;

    SearchCriteria searchCriteriaWithItemName;
    SearchCriteria searchCriteriaWithCategory;
    SearchCriteria searchCriteriaWithBrand;
    SearchCriteria searchCriteriaWithSize;
    SearchCriteria searchCriteriaWithPrice;
    SearchCriteria searchCriteriaWithGender;

    SearchCriteria searchCriteriaWithPopular;

    SearchCriteria searchCriteriaWithPremiumDesc;

    SearchCriteria searchCriteriaWithPremiumAsc;

    SearchCriteria searchCriteriaWithLowestSellingPrice;

    SearchCriteria searchCriteriaWithHighestPurchasePrice;

    SearchCriteria searchCriteriaWithReleaseDate;


    @BeforeEach
    void beforeEach() {
        searchCriteriaWithItemName = SearchCriteria.builder().context("fix").build();
        searchCriteriaWithCategory = SearchCriteria.builder()
            .categoryId(new Long[]{3L})
            .build();
        searchCriteriaWithBrand = SearchCriteria.builder().brandId(25L).build();
        searchCriteriaWithSize = SearchCriteria.builder().size("270").build();
        searchCriteriaWithPrice = SearchCriteria.builder().minPrice(35000).maxPrice(45000).build();
        searchCriteriaWithGender = SearchCriteria.builder().gender(ItemGender.MALE).build();
        searchCriteriaWithPopular = SearchCriteria.builder().sort(SortCriteria.POPULAR).build();
        searchCriteriaWithPremiumAsc = SearchCriteria.builder().sort(SortCriteria.PREMIUM_ASC)
            .build();
        searchCriteriaWithPremiumDesc = SearchCriteria.builder().sort(SortCriteria.PREMIUM_DESC)
            .build();
        searchCriteriaWithLowestSellingPrice = SearchCriteria.builder()
            .sort(SortCriteria.LOWEST_SELLING_PRICE).build();
        searchCriteriaWithHighestPurchasePrice = SearchCriteria.builder()
            .sort(SortCriteria.HIGHEST_PURCHASE_PRICE).build();
        searchCriteriaWithReleaseDate = SearchCriteria.builder().sort(SortCriteria.RELEASE_DATE)
            .build();
    }


    @Test
    void 전체검색_거래량순() {
        List<SearchItemDto> searchItemDtos = searchMapper.searchAll();
        assertThat(searchItemDtos.get(0).getSortCriteria()).isGreaterThanOrEqualTo(
            searchItemDtos.get(1).getSortCriteria());
    }

    @Test
    void 전체검색시_프리미엄_낮은순() {
        List<SearchItemDto> searchItemDtos = searchMapper.searchByCriteria(
            searchCriteriaWithPremiumAsc);
        Long itemId = searchItemDtos.get(0).getItemId();
        Item item = itemMapper.findOne(itemId);
        assertThat(item.getReleasedPrice()).isGreaterThanOrEqualTo(
            searchItemDtos.get(0).getPrice());
        assertThat(Double.valueOf(searchItemDtos.get(0).getSortCriteria())).isLessThanOrEqualTo(
            Double.valueOf(searchItemDtos.get(1).getSortCriteria()));

    }

    @Test
    void 전체검색시_프리미엄_높은순() {
        List<SearchItemDto> searchItemDtos = searchMapper.searchByCriteria(
            searchCriteriaWithPremiumDesc);
        Long itemId = searchItemDtos.get(0).getItemId();
        Item item = itemMapper.findOne(itemId);
        assertThat(item.getReleasedPrice()).isLessThanOrEqualTo(searchItemDtos.get(0).getPrice());
        assertThat(Double.valueOf(searchItemDtos.get(0).getSortCriteria())).isGreaterThanOrEqualTo(
            Double.valueOf(searchItemDtos.get(1).getSortCriteria()));

    }

    @Test
    void 전체검색시_최저판매가순() {
        List<SearchItemDto> searchItemDtos = searchMapper.searchByCriteria(
            searchCriteriaWithLowestSellingPrice);
        assertThat(searchItemDtos.get(0).getPrice()).isLessThanOrEqualTo(
            searchItemDtos.get(1).getPrice());
    }

    @Test
    void 전체검색시_최고구매가순() {
        List<SearchItemDto> searchItemDtos = searchMapper.searchByCriteria(
            searchCriteriaWithHighestPurchasePrice);
        assertThat(searchItemDtos.get(0).getPrice()).isGreaterThanOrEqualTo(
            searchItemDtos.get(1).getPrice());
    }

    @Test
    void 전체검색시_발매일순() {
        List<SearchItemDto> searchItemDtos = searchMapper.searchByCriteria(
            searchCriteriaWithReleaseDate);
        Item item1 = itemMapper.findOne(searchItemDtos.get(0).getItemId());
        Item item2 = itemMapper.findOne(searchItemDtos.get(1).getItemId());
        assertThat(item1.getReleaseDate()).isAfter(item2.getReleaseDate());
    }

    @Test
    void 검색_기본() {
        assertThat(searchMapper.searchByCriteria(searchCriteriaWithItemName).get(0)
            .getItemName()).contains("fix");
    }

    @Test
    void 검색_브랜드() {
        assertThat(
            searchMapper.searchByCriteria(searchCriteriaWithBrand).get(0).getBrandId()).isEqualTo(
            searchCriteriaWithBrand.getBrandId());
    }

    @Test
    void 검색_사이즈() {
        Long itemId = searchMapper.searchByCriteria(searchCriteriaWithSize).get(0).getItemId();
        List<ItemSizePrice> itemSizePrices = itemSizePriceMapper.findAllByItemId(itemId);
        List<String> sizes = itemSizePrices.stream().map(x -> x.getSize())
            .collect(Collectors.toList());
        assertThat(sizes).contains(searchCriteriaWithSize.getSize());
    }

    @Test
    void 검색_성별() {
        Long itemId = searchMapper.searchByCriteria(searchCriteriaWithGender).get(0).getItemId();
        Item item = itemMapper.findOne(itemId);
        assertThat(item.getGender()).isEqualTo(ItemGender.MALE);
    }

    @Test
    void 검색_카테고리() {
        List<SearchItemDto> searchItemDtos = searchMapper.searchByCriteria(
            searchCriteriaWithCategory);
        Long itemId = searchItemDtos.get(0).getItemId();

        assertThat(itemMapper.findOne(itemId).getCategoryId()).isEqualTo(
            searchCriteriaWithCategory.getCategoryId()[0]);
    }

    @Test
    void findAllCount() {
        assertThat(searchMapper.findAllCount()).isEqualTo(100);
    }

    @Test
    void findCountByCriteria() {
        assertThat(searchMapper.findCountByCriteria(searchCriteriaWithCategory)).isEqualTo(13);
    }

//    @Test
//    void autoComplete() {
//        assertThat(searchMapper.autoComplete(List.of("ve")).get(0).getItemName()).contains("ve");
//    }
}