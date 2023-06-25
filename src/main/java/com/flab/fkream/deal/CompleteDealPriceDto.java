package com.flab.fkream.deal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteDealPriceDto {

    private Long itemId;
    private int price;

}
