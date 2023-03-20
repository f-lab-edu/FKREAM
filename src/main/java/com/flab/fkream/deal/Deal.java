package com.flab.fkream.deal;

import java.time.LocalDate;

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

}
