package com.flab.fkream.deal;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DealHistoryDto {

    private Long dealId;
    private Long itemId;
    private String itemName;
    private int price;
    private LocalDate period;
    private Long itemImgId;
    private String imgUrl;

}
