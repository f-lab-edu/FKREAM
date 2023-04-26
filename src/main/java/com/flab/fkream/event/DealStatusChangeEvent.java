package com.flab.fkream.event;

import com.flab.fkream.deal.Deal;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class DealStatusChangeEvent extends ApplicationEvent {

    @Getter
    private Deal deal;

    public DealStatusChangeEvent(Object source, Deal deal) {
        super(source);
        this.deal = deal;
    }
}
