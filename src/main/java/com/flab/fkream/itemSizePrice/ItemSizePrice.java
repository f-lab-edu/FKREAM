package com.flab.fkream.itemSizePrice;

import java.time.LocalDateTime;
import java.util.List;

import com.flab.fkream.ownedItems.OwnedItem;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@Builder
public class ItemSizePrice {
	private Long id;
	private Long ItemId;
	private final String size;
	private final int lowestSellingPrice;
	private final int highestPurchasePrice;
	private LocalDateTime modifiedAt;
	private List<OwnedItem> ownedItemList;
}
