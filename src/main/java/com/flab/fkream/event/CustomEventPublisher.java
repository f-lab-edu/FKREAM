package com.flab.fkream.event;

import com.flab.fkream.deal.Deal;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishInstantPurchasePriceChangeEvent(Long itemSizePriceId,
        final String itemName) {
        InstantPurchasePriceChangeEvent event = new InstantPurchasePriceChangeEvent(
            this, itemSizePriceId, itemName);
        eventPublisher.publishEvent(event);
    }

    public void publishDealStatusChangeEvent(Deal deal) {
        DealStatusChangeEvent event = new DealStatusChangeEvent(this, deal);
        eventPublisher.publishEvent(event);
    }

}
