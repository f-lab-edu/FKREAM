package com.flab.fkream.item;

import java.time.LocalDateTime;
import java.util.List;

import com.flab.fkream.itemImg.ItemImg;

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
public class Item {
	private Long id;
	private final String itemName;
	private final String modelNumber;
	private final String category1;
	private final String category2;
	private final LocalDateTime releaseDate;
	private final String representativeColor;
	private final int releasedPrice;
	private final int latestPrice;
	private final Long brandId;
	private final Long managerId;
	private final LocalDateTime created_at;
	private LocalDateTime modified_at;
}


