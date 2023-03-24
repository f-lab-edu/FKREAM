package com.flab.fkream.ownedItems;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnedItem {
	private Long id;
	private Long userId;
	private Long itemSizePriceId;
	private int purchasePrice;
}
