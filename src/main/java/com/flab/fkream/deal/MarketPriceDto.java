package com.flab.fkream.deal;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
public class MarketPriceDto implements Serializable {


    private String size;

    private int price;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tradingDay;
}
