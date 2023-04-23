package com.flab.fkream.search;

import com.flab.fkream.item.ItemGender;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria implements Serializable {

    private String context;
    private ItemGender gender;
    private Long brandId;
    private String size;
    private Integer minPrice;
    private Integer maxPrice;
    private Long[] categoryId;
    private SortCriteria sort;

    private static final String BAD_REQUEST_CRITERIA = "필터 기준을 잘못 입력했습니다";

    public void validatePrice() {
        try {
            if (minPrice != null && maxPrice != null) {
                if (minPrice > maxPrice) {
                    throw new IllegalArgumentException();
                }
            }
            if (minPrice == null && maxPrice != null) {
                throw new IllegalArgumentException();
            }
            if (minPrice != null && maxPrice == null) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(BAD_REQUEST_CRITERIA);
        }
    }
}
