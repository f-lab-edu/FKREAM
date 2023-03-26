package com.flab.fkream.itemImg;

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
public class ItemImg {
	private Long id;
	private Long itemId;
	private String imgName;
	private String imgUrl;
	private String originName;
	private String repImgYn;
	private LocalDateTime createdAt;
}
