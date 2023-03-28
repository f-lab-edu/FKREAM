package com.flab.fkream.item;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
	private Long id;
	@NonNull
	private String itemName;
	@NonNull
	private String modelNumber;
	@NonNull
	private String category1;
	@NonNull
	private String category2;
	private LocalDateTime releaseDate;
	@NonNull
	private String representativeColor;
	@NonNull
	private int releasedPrice;
	private int latestPrice;
	private Long brandId;
	private Long managerId;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
}


