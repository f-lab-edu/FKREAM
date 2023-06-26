package com.flab.fkream.elasticsearch;

import com.flab.fkream.item.ItemGender;
import com.flab.fkream.search.SortCriteria;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ElasticSearchCriteria implements Serializable {

    private String context;
    private ItemGender gender;
    private Long brandId;
    private String[] size;
    private Integer minPrice;
    private Integer maxPrice;
    private Long[] categoryId;
    private Long[] detailedCategoryId;
    private SortCriteria sort;
    private Object[] sortValue;

    private static final String BAD_REQUEST_CRITERIA = "필터 기준을 잘못 입력했습니다";

    public void validatePrice() {
        if (minPrice != null && maxPrice != null) {
            if (minPrice < 0 || maxPrice < 0) {
                throw new IllegalArgumentException("최소, 최대 가격은 음수일 수 없습니다.");
            }
            if (minPrice > maxPrice) {
                throw new IllegalArgumentException("최소 가격이 최대 가격보다 클 수 없습니다.");
            }
        }
        if (minPrice == null ^ maxPrice == null) {
            throw new IllegalArgumentException("최소 가격과 최대 가격을 하나만 입력할 수 없습니다.");
        }
    }
}
