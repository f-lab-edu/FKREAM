package com.flab.fkream.elasticsearch;

import com.flab.fkream.brand.Brand;
import com.flab.fkream.item.ItemGender;
import java.time.LocalDate;
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

    @Id
    private Long id;

    private String itemName;

    private List<String> size;

    private String modelNumber;

    private Long categoryId;

    private Long detailedCategoryId;

    @Field(type = FieldType.Date)
    private LocalDate releaseDate;

    private int releasedPrice;

    private ItemGender gender;

    private Brand brand;

    private int dealCount;

    private int premiumRate;

    private int immediateSalePrice;

    public static ItemDocument of(SearchHit hit) {
        Map<String, Object> sourceAsMap = hit.getSourceAsMap();

        ItemDocument itemDocument = ItemDocument.builder()
            .id(Long.parseLong(hit.getId()))
            .itemName((String) sourceAsMap.get("itemName"))
            .size((List<String>) sourceAsMap.get("size"))
            .modelNumber((String) sourceAsMap.get("modelNumber"))
            .categoryId(((Number) sourceAsMap.get("categoryId")).longValue())
            .detailedCategoryId(((Number) sourceAsMap.get("detailedCategoryId")).longValue())
            .releaseDate(parseLocalDate(sourceAsMap.get("releaseDate")))
            .releasedPrice((int) sourceAsMap.get("releasedPrice"))
            .gender(ItemGender.valueOf((String) sourceAsMap.get("gender")))
            .brand(parseBrand((Map<String, Object>) sourceAsMap.get("brand")))
            .dealCount((int) sourceAsMap.get("dealCount"))
            .premiumRate((int) sourceAsMap.get("premiumRate"))
            .immediateSalePrice((int) sourceAsMap.get("immediateSalePrice"))
            .build();
        return itemDocument;
    }

    private static LocalDate parseLocalDate(Object value) {
        if (value instanceof String) {
            return LocalDate.parse((String) value);
        } else if (value instanceof Long) {
            return LocalDate.ofEpochDay((Long) value);
        }
        return null;
    }

    private static Brand parseBrand(Map<String, Object> brandMap) {
        Brand brand = Brand.builder()
            .id(((Number) brandMap.get("id")).longValue())
            .brandName((String) brandMap.get("brandName"))
            .isLuxury((boolean) brandMap.get("isLuxury"))
            .build();
        return brand;
    }
}
