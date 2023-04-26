package com.flab.fkream.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class InstantPurchasePriceChangeEvent extends ApplicationEvent {

    private Long itemSizePriceId;
    private String itemName;

    public InstantPurchasePriceChangeEvent(Object source, Long itemSizePriceId, String itemName) {
        super(source);
        this.itemSizePriceId = itemSizePriceId;
        this.itemName = itemName;
    }
}
