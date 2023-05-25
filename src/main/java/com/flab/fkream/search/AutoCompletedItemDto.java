package com.flab.fkream.search;

import com.flab.fkream.item.Item;
import com.flab.fkream.itemImg.ItemImg;
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

    public static AutoCompletedItemDto of(Item item, ItemImg itemImg) {
        return new AutoCompletedItemDto(item.getId(), item.getItemName(), itemImg.getId(),
            itemImg.getImgName(), itemImg.getImgUrl());
    }
}
