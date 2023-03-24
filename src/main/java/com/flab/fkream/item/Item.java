package com.flab.fkream.item;

import java.time.LocalDateTime;

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
public class Item {
	private Long id;
	private String itemName;
	private String modelNumber;
	private String category1;
	private String category2;
	private LocalDateTime releaseDate;
	private String representativeColor;
	private int releasedPrice;
	private int latestPrice;
	private Long brandId;
	private Long managerId;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
}


