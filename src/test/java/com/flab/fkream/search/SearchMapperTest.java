package com.flab.fkream.search;

import static org.assertj.core.api.Assertions.*;

import com.flab.fkream.brand.Brand;
import com.flab.fkream.brand.BrandMapper;
import com.flab.fkream.deal.Deal;
import com.flab.fkream.deal.DealMapper;
import com.flab.fkream.item.Item;
import com.flab.fkream.item.ItemGender;
import com.flab.fkream.item.ItemMapper;
import com.flab.fkream.itemCategory.ItemCategory;
import com.flab.fkream.itemCategory.ItemCategoryMapper;
import com.flab.fkream.itemImg.ItemImg;
import com.flab.fkream.itemImg.ItemImgMapper;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.itemSizePrice.ItemSizePriceMapper;
import com.flab.fkream.user.User;
import com.flab.fkream.user.UserMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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

    User userInfo1;
    User userInfo2;

    Brand brandInfo1;
    Brand brandInfo2;

    ItemCategory itemCategoryInfo1;
    ItemCategory itemCategoryInfo2;
    ItemCategory detailedCategoryInfo1;
    ItemCategory detailedCategoryInfo2;

    Item itemInfo1;
    Item itemInfo2;
    Item itemInfo3;

    ItemImg itemImgInfo1;
    ItemImg itemImgInfo2;
    ItemImg itemImgInfo3;
    ItemImg itemImgInfo4;

    ItemSizePrice itemSizePriceInfo1;
    ItemSizePrice itemSizePriceInfo2;
    ItemSizePrice itemSizePriceInfo3;
    ItemSizePrice itemSizePriceInfo4;
    ItemSizePrice itemSizePriceInfo5;
    ItemSizePrice itemSizePriceInfo6;

    Deal dealInfo1;
    Deal dealInfo2;
    Deal dealInfo3;
    Deal dealInfo4;
    Deal dealInfo5;

    SearchItemDto searchItemDto1;
    SearchItemDto searchItemDto2;

    AutoCompletedItemDto autoCompletedItemDto;

    SearchCriteria searchCriteriaWithItemName;
    SearchCriteria searchCriteriaWithCategory;
    SearchCriteria searchCriteriaWithBrand;
    SearchCriteria searchCriteriaWithSize;
    SearchCriteria searchCriteriaWithPrice;
    SearchCriteria searchCriteriaWithGender;
    SearchCriteria searchCriteria7;


    @BeforeEach
    void beforeEach() {
        userInfo1 = User.builder().email("email1").build();
        userInfo2 = User.builder().email("email2").build();
        userMapper.save(userInfo1);
        userMapper.save(userInfo2);

        brandInfo1 = Brand.builder().brandName("nike").build();
        brandInfo2 = Brand.builder().brandName("adidas").build();
        brandMapper.save(brandInfo1);
        brandMapper.save(brandInfo2);

        itemCategoryInfo1 = ItemCategory.builder().name("신발").build();
        itemCategoryInfo2 = ItemCategory.builder().name("상의").build();
        itemCategoryMapper.save(itemCategoryInfo1);
        itemCategoryMapper.save(itemCategoryInfo2);

        detailedCategoryInfo1 = ItemCategory.builder().name("스니커즈")
            .parentId(itemCategoryInfo1.getId()).build();
        detailedCategoryInfo2 = ItemCategory.builder().name("후드")
            .parentId(itemCategoryInfo2.getId()).build();
        itemCategoryMapper.save(detailedCategoryInfo1);
        itemCategoryMapper.save(detailedCategoryInfo2);

        itemInfo1 = Item.builder().itemName("조던").modelNumber("jd-100").brand(brandInfo1)
            .categoryId(itemCategoryInfo1.getId()).detailedCategoryId(detailedCategoryInfo1.getId())
            .gender(ItemGender.MALE)
            .build();
        itemInfo2 = Item.builder().itemName("에어포스").modelNumber("af-200").brand(brandInfo1)
            .categoryId(itemCategoryInfo1.getId()).detailedCategoryId(detailedCategoryInfo1.getId())
            .gender(ItemGender.FEMALE)
            .build();
        itemInfo3 = Item.builder().itemName("후드티").modelNumber("ss-300").brand(brandInfo2)
            .categoryId(itemCategoryInfo2.getId()).detailedCategoryId(detailedCategoryInfo2.getId())
            .gender(ItemGender.KIDS)
            .build();
        itemMapper.save(itemInfo1);
        itemMapper.save(itemInfo2);
        itemMapper.save(itemInfo3);

        itemImgInfo1 = ItemImg.builder().item(itemInfo1).imgName("빨간색 조던").imgUrl("imgUrl1")
            .isRepresentativeImg(true).build();
        itemImgInfo2 = ItemImg.builder().item(itemInfo1).imgName("빨간색 조던 뒷").imgUrl("imgUrl2")
            .isRepresentativeImg(false).build();
        itemImgInfo3 = ItemImg.builder().item(itemInfo2).imgName("하얀색 에어포스").imgUrl("imgUrl3")
            .isRepresentativeImg(true).build();
        itemImgInfo4 = ItemImg.builder().item(itemInfo3).imgName("검은색 삼선스리퍼").imgUrl("imgUrl4")
            .isRepresentativeImg(true).build();
        itemImgMapper.save(itemImgInfo1);
        itemImgMapper.save(itemImgInfo2);
        itemImgMapper.save(itemImgInfo3);
        itemImgMapper.save(itemImgInfo4);

        itemSizePriceInfo1 = ItemSizePrice.builder().itemId(itemInfo1.getId()).size("250")
            .lowestSellingPrice(20000).highestPurchasePrice(15000).build();
        itemSizePriceInfo2 = ItemSizePrice.builder().itemId(itemInfo1.getId()).size("260")
            .lowestSellingPrice(30000).highestPurchasePrice(25000).build();
        itemSizePriceInfo3 = ItemSizePrice.builder().itemId(itemInfo2.getId()).size("270")
            .lowestSellingPrice(40000).highestPurchasePrice(35000).build();
        itemSizePriceInfo4 = ItemSizePrice.builder().itemId(itemInfo2.getId()).size("280")
            .lowestSellingPrice(50000).highestPurchasePrice(45000).build();
        itemSizePriceInfo5 = ItemSizePrice.builder().itemId(itemInfo3.getId()).size("290")
            .lowestSellingPrice(60000).highestPurchasePrice(55000).build();
        itemSizePriceInfo6 = ItemSizePrice.builder().itemId(itemInfo3.getId()).size("300")
            .lowestSellingPrice(70000).highestPurchasePrice(65000).build();
        itemSizePriceMapper.save(itemSizePriceInfo1);
        itemSizePriceMapper.save(itemSizePriceInfo2);
        itemSizePriceMapper.save(itemSizePriceInfo3);
        itemSizePriceMapper.save(itemSizePriceInfo4);
        itemSizePriceMapper.save(itemSizePriceInfo5);
        itemSizePriceMapper.save(itemSizePriceInfo6);

        dealInfo1 = Deal.builder().item(itemInfo1).user(userInfo1).price(14000).size("250").build();
        dealInfo2 = Deal.builder().item(itemInfo1).user(userInfo1).price(24000).size("260").build();
        dealInfo3 = Deal.builder().item(itemInfo2).user(userInfo2).price(34000).size("270").build();
        dealInfo4 = Deal.builder().item(itemInfo2).user(userInfo2).price(44000).size("280").build();
        dealInfo5 = Deal.builder().item(itemInfo2).user(userInfo1).price(34000).size("270").build();
        dealMapper.save(dealInfo1);
        dealMapper.save(dealInfo2);
        dealMapper.save(dealInfo3);
        dealMapper.save(dealInfo4);
        dealMapper.save(dealInfo5);

        searchItemDto1 = SearchItemDto.builder().itemId(itemInfo1.getId())
            .itemName(itemInfo1.getItemName()).brandId(itemInfo1.getBrand().getId()).brandName(
                itemInfo1.getBrand().getBrandName()).buyNowLowestPrice(20000).itemImgId(
                itemImgInfo1.getId()).imgName(itemImgInfo1.getImgName())
            .imgUrl(itemImgInfo1.getImgUrl()).build();
        searchItemDto2 = SearchItemDto.builder().itemId(itemInfo3.getId())
            .itemName(itemInfo3.getItemName()).brandId(itemInfo3.getBrand().getId()).brandName(
                itemInfo3.getBrand().getBrandName()).buyNowLowestPrice(60000).itemImgId(
                itemImgInfo4.getId()).imgName(itemImgInfo4.getImgName())
            .imgUrl(itemImgInfo4.getImgUrl()).build();

        autoCompletedItemDto = AutoCompletedItemDto.builder().itemId(itemInfo1.getId())
            .itemName(itemInfo1.getItemName()).itemImgId(itemImgInfo1.getId()).imgName(
                itemImgInfo1.getImgName()).imgUrl(itemImgInfo1.getImgUrl()).build();

        searchCriteriaWithItemName = SearchCriteria.builder().context("조던").build();
        searchCriteriaWithCategory = SearchCriteria.builder()
            .categoryId(new Long[]{itemCategoryInfo1.getId()})
            .build();
        searchCriteriaWithBrand = SearchCriteria.builder().brandId(brandInfo2.getId()).build();
        searchCriteriaWithSize = SearchCriteria.builder().size("270").build();
        searchCriteriaWithPrice = SearchCriteria.builder().minPrice(35000).maxPrice(45000).build();
        searchCriteriaWithGender = SearchCriteria.builder().gender(ItemGender.MALE.toString()).build();

    }


    @Test
    void searchAll() {
        assertThat(searchMapper.searchAll()).contains(searchItemDto1).hasSize(3);
    }

    @Test
    void search_아이템명() {
        assertThat(searchMapper.searchByCriteria(searchCriteriaWithItemName)).contains(
            searchItemDto1).hasSize(1);
    }

    @Test
    void search_브랜드명() {
        assertThat(
            searchMapper.searchByCriteria(searchCriteriaWithBrand).get(0).getBrandId()).isEqualTo(
            brandInfo2.getId());
    }

    @Test
    void search_사이즈() {
        assertThat(
            searchMapper.searchByCriteria(searchCriteriaWithSize).get(0).getItemName()).isEqualTo(
            "에어포스");
    }

    @Test
    void search_성별() {
        assertThat(
            searchMapper.searchByCriteria(searchCriteriaWithGender).get(0).getItemName()).isEqualTo(
            "조던");
    }

    @Test
    void search_카테고리() {
        assertThat(searchMapper.searchByCriteria(searchCriteriaWithCategory)).hasSize(2);
    }

    @Test
    void findAllCount() {
        assertThat(searchMapper.findAllCount()).isEqualTo(3);
    }

    @Test
    void findCountByCriteria() {
        assertThat(searchMapper.findCountByCriteria(searchCriteriaWithCategory)).isEqualTo(2);
    }

    @Test
    void autoComplete() {
        assertThat(searchMapper.autoComplete(List.of("조던"))).contains(autoCompletedItemDto);
    }
}