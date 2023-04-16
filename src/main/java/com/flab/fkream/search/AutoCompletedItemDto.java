package com.flab.fkream.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AutoCompletedItemDto {

    private Long itemId;

    private String itemName;

    private Long itemImgId;

    private String imgName;

    private String imgUrl;
}
