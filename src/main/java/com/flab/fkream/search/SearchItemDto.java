package com.flab.fkream.search;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchItemDto {

    private Long itemId;

    private String itemName;

    private Long brandId;

    private String brandName;

    private int buyNowLowestPrice;

    private Long itemImgId;

    private String imgName;

    private String imgUrl;

}
