package com.flab.fkream.brand;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
  private Long id;
	@NonNull
	private String brandName;
	private boolean isLuxury;
}
