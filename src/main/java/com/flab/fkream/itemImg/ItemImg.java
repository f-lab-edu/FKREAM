package com.flab.fkream.itemImg;

import java.time.LocalDateTime;

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
public class ItemImg {
	private Long id;
	private final Long itemId;
	private final String imgName;
	private final String imgUrl;
	private final String originName;
	private final String repImgYn;
	private final LocalDateTime createdAt;
}
