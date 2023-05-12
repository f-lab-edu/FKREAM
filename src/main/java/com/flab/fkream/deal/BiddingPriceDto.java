package com.flab.fkream.deal;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BiddingPriceDto implements Serializable {

    private String size;

    private int price;

    private int count;
}
