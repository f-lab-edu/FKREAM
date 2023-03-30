package com.flab.fkream.itemImg;

import java.time.LocalDateTime;

import com.flab.fkream.item.Item;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemImg {
	private Long id;
	@NonNull
	private Item item;
	@NonNull
	private String imgName;
	@NonNull
	private String imgUrl;
	@NonNull
	private String originName;
	private boolean isRepresentativeImg;
	private LocalDateTime createdAt;
}
