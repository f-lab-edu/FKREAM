package com.flab.fkream.myItems;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
public class MyItem {

    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private Long itemSizePriceId;
    @Min(value = 30000, message = "구매가는 적어도 30,000 이상이어야 합니다.")
    private int purchasePrice;
}
