package com.flab.fkream.search;

import com.flab.fkream.item.ItemGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {

    private String context;
    private String gender;
    private Long brandId;
    private String size;
    private Integer minPrice;
    private Integer maxPrice;
    private Long[] categoryId;
    private static final String BAD_REQUEST_CRITERIA = "필터 기준을 잘못 입력했습니다";

    public void Validation() {
        try {
            if (gender != null) {
                ItemGender.valueOf(gender);
            }
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
