package com.flab.fkream.AutoComplete;

import com.flab.fkream.item.Item;
import com.flab.fkream.itemImg.ItemImg;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AutoCompletedItemDto implements Comparable<AutoCompletedItemDto> {

    private Long itemId;

    private String itemName;

    private int count;

    private Long itemImgId;

    private String imgName;

    private String imgUrl;

    public static AutoCompletedItemDto of(Item item) {
        return AutoCompletedItemDto.builder().itemId(item.getId()).itemName(item.getItemName()).build();
    }

    public static List<AutoCompletedItemDto> of(List<Item> items) {
        return new ArrayList<>();
    }

    @Override
    public int compareTo(AutoCompletedItemDto o) {
        return Integer.compare(this.count, o.getCount());
    }
}
