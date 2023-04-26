package com.flab.fkream.event;


import com.flab.fkream.deal.Deal;
import com.flab.fkream.fcm.FCMTokenService;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.itemSizePrice.ItemSizePriceService;
import com.flab.fkream.notification.Notification;
import com.flab.fkream.notification.NotificationService;
import com.flab.fkream.notification.NotificationType;
import com.flab.fkream.notification.TitleInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CustomEventListener {

    private final NotificationService notificationService;
    private final FCMTokenService fcmTokenService;
    private final ItemSizePriceService itemSizePriceService;


    @EventListener
    @Async("InstantPurchasePriceChangeEventAsync")
    @Transactional
    public void handleInstantPurchasePriceChangeEvent(InstantPurchasePriceChangeEvent event) {
        Long itemSizePriceId = event.getItemSizePriceId();

        // itemSizePriceId로 해당 아이템을 찜한 유저 아이디를 가져오는 기능 구현
        List<Long> userIds = List.of(); // 기능 구현 전까지만 사용

        List<String> fcmTokens = userIds.stream()
            .map(fcmTokenService::findToken)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        notificationService.sendPriceChangeNotification(fcmTokens, event.getItemName());

        ItemSizePrice itemSizePrice = itemSizePriceService.findOne(event.getItemSizePriceId());

        TitleInfo titleInfo = notificationService.getTitleInfoForPurchasePrice(
            event.getItemName());

        List<Notification> notifications = userIds.stream()
            .map(userId -> Notification.builder()
                .userId(userId)
                .itemId(itemSizePrice.getItemId())
                .title(titleInfo.getTitle())
                .context(titleInfo.getContext())
                .notificationType(NotificationType.CHANGED_PRICE_OF_INTERESTED_ITEM)
                .createdAt(LocalDateTime.now()).build())
            .collect(Collectors.toList());

        notificationService.saveAll(notifications);
    }

    @EventListener
    @Async("DealStatusChangeEventAsync")
    public void handleDealStatusChangeEvent(DealStatusChangeEvent event) {

        Deal deal = event.getDeal();
        String token = fcmTokenService.findToken(deal.getUserId());
        notificationService.sendDealStatusChangeNotification(token, deal);

        TitleInfo titleInfo = notificationService.getTitleInfoForDealStatus(deal);

        Notification notification = Notification.builder()
            .userId(deal.getUserId())
            .dealId(deal.getId())
            .title(titleInfo.getTitle())
            .context(titleInfo.getContext())
            .notificationType(NotificationType.UPDATE_STATE_OF_ITEM)
            .createdAt(LocalDateTime.now()).build();

        notificationService.save(notification);
    }

}
