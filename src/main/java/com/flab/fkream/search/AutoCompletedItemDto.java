package com.flab.fkream.search;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AutoCompletedItemDto implements Serializable {

    private Long itemId;

    private String itemName;

    private Long itemImgId;

    private String imgName;

    private String imgUrl;
}
