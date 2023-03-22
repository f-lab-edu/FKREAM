package com.flab.fkream.ownedItems;

import com.flab.fkream.itemSizePrice.ItemSizePrice;

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
public class OwnedItem {
	private Long id;
	private final Long userId;
	private final ItemSizePrice itemSizePrice;
	private final int purchasePrice;
}
