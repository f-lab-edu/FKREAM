package com.flab.fkream.ownedItems;

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
public class OwnedItem {

    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private Long itemSizePriceId;
    @Min(value = 30000, message = "Purchase price must be at least 30,000")
    private int purchasePrice;
}
