package com.flab.fkream.event;


import com.flab.fkream.deal.Deal;
import com.flab.fkream.fcm.FCMTokenService;
import com.flab.fkream.interestedItem.InterestedItem;
import com.flab.fkream.interestedItem.InterestedItemService;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.itemSizePrice.ItemSizePriceService;
import com.flab.fkream.notification.Notification;
import com.flab.fkream.notification.NotificationService;
import com.flab.fkream.notification.NotificationType;
import com.flab.fkream.notification.TitleInfo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
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

    private final InterestedItemService interestedItemService;


    @EventListener
    @Async("InstantPurchasePriceChangeEventAsync")
    @Transactional
    public void handleInstantPurchasePriceChangeEvent(InstantPurchasePriceChangeEvent event) {
        Long itemSizePriceId = event.getItemSizePriceId();

        List<Long> userIds = interestedItemService.findUserIdsByItemSizePriceID(
            itemSizePriceId);

        Map<Long, String> userIdToTokenMap = userIds.stream()
            .collect(Collectors.toMap(
                Function.identity(),
                fcmTokenService::findToken
            ));

        List<String> failedTokens = notificationService.sendPriceChangeNotification(
            new ArrayList<>(userIdToTokenMap.values()), event.getItemName());

        List<Long> successUserIds = userIdToTokenMap.entrySet().stream()
            .filter(entry -> !failedTokens.contains(entry.getValue()))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());

        ItemSizePrice itemSizePrice = itemSizePriceService.findOne(event.getItemSizePriceId());

        TitleInfo titleInfo = notificationService.getTitleInfoForPurchasePrice(
            event.getItemName());

        List<Notification> notifications = successUserIds.stream()
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
        List<String> failedTokens = notificationService.sendDealStatusChangeNotification(token,
            deal);

        if (!failedTokens.isEmpty()) {
            return;
        }

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
