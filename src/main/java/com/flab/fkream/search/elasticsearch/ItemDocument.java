package com.flab.fkream.search.elasticsearch;

import com.flab.fkream.brand.Brand;
import com.flab.fkream.item.Item;
import com.flab.fkream.item.ItemGender;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.elasticsearch.search.SearchHit;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "item")
@Mapping(mappingPath = "elastic/item-mapping.json")
@Setting(settingPath = "elastic/item-setting.json")
public class ItemDocument {

    // 검색 (상품 이름, 브랜드명, 모델 넘버)
    @Id
    private Long id;

    private String itemName;
    private Brand brand;
    private String modelNumber;

    // 필터 (사이즈, 카테고리, 성별)
    private List<String> size;
    private Long categoryId;
    private Long detailedCategoryId;
    private ItemGender gender;
    private int releasedPrice;

    // 정렬 (발매일 순, 거래 순, 프리미엄율 순)
    @Field(type = FieldType.Date)
    private LocalDate releaseDate;

    private int dealCount;
    private Map<String, Integer> immediatePurchasePriceBySize;
    private Map<String, Integer> premiumRateBySize;
    private int minPremiumRate;


    public static ItemDocument of(SearchHit hit) {
        Map<String, Object> sourceAsMap = hit.getSourceAsMap();

        ItemDocument itemDocument = ItemDocument.builder()
            .id(Long.parseLong(hit.getId()))
            .itemName((String) sourceAsMap.get("itemName"))
          /*  .size((List<String>) sourceAsMap.get("size"))
            .immediatePurchasePriceBySize(
                (Map<String, Integer>) sourceAsMap.get("immediatePurchasePriceBySize"))
            .modelNumber((String) sourceAsMap.get("modelNumber"))
            .categoryId(((Number) sourceAsMap.get("categoryId")).longValue())
            .detailedCategoryId(((Number) sourceAsMap.get("detailedCategoryId")).longValue())
            .releaseDate(parseLocalDate(sourceAsMap.get("releaseDate")))
            .releasedPrice((int) sourceAsMap.get("releasedPrice"))
            .gender(ItemGender.valueOf((String) sourceAsMap.get("gender")))*/
            .brand(parseBrand((Map<String, Object>) sourceAsMap.get("brand")))
            .dealCount((int) sourceAsMap.get("dealCount"))
           /* .premiumRateBySize(
                (Map<String, Integer>) sourceAsMap.get("premiumRateBySize"))
            .minPremiumRate((int) sourceAsMap.get("minPremiumRate"))*/
            .build();
        return itemDocument;
    }

    public static ItemDocument of(Item item, List<String> sizes) {
        Map<String, Integer> premiumRateBySize = new HashMap<>();

        for (String size : sizes) {
            premiumRateBySize.put(size, 0);
        }

        ItemDocument itemDocument = ItemDocument.builder()
            .id(item.getId())
            .itemName(item.getItemName())
            .size(sizes)
            .modelNumber(item.getModelNumber())
            .categoryId(item.getCategoryId())
            .detailedCategoryId(item.getDetailedCategoryId())
            .releaseDate(item.getReleaseDate())
            .releasedPrice(item.getReleasedPrice())
            .gender(item.getGender())
            .brand(item.getBrand())
            .dealCount(0)
            .premiumRateBySize(premiumRateBySize)
            .minPremiumRate(0)
            .build();
        return itemDocument;
    }

    public void increaseCount() {
        this.dealCount++;
    }

    public void updatePremiumRateBySize(String size, int latestPrice) {
        double premiumRate = (double) (latestPrice - releasedPrice) / releasedPrice * 100;
        this.premiumRateBySize.put(size, (int) premiumRate);
    }

    public void updateMinPremiumRate() {
        this.minPremiumRate = this.premiumRateBySize.values()
            .stream()
            .min(Integer::compareTo)
            .orElse(this.minPremiumRate);
    }

    private static LocalDate parseLocalDate(Object value) {
        if (value instanceof String) {
            return LocalDate.parse((String) value);
        } else if (value instanceof Long) {
            return LocalDate.ofEpochDay((Long) value);
        }
        throw new IllegalArgumentException();
    }

    private static Brand parseBrand(Map<String, Object> brandMap) {
        Brand brand = Brand.builder()
            .id(((Number) brandMap.get("id")).longValue())
            .brandName((String) brandMap.get("brandName"))
            .luxury((boolean) brandMap.get("luxury"))
            .build();
        return brand;
    }

}
