package com.flab.fkream.deal;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class Deal {

    private Long id;
    private final Item item;
    private final KindOfDeal kindOfDeal;
    private final Users users;
    private final int price;
    private final String size;
    private final LocalDate period;
    private final boolean utilizationPolicy;
    private final boolean salesCondition;
    private final Status status;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;
}
