package com.flab.fkream.search.dbSearch;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchItemDto implements Serializable {

    private Long itemId;

    private String itemName;

    private Long brandId;

    private String brandName;

    private int price;

    private Long itemImgId;

    private String imgName;

    private String imgUrl;

    private String sortCriteria;
}
