package com.flab.fkream.notification;


import com.flab.fkream.deal.Deal;
import com.flab.fkream.deal.DealType;
import com.flab.fkream.deal.Status;
import com.flab.fkream.error.exception.NoDataFoundException;
import com.flab.fkream.error.exception.NotOwnedDataException;
import com.flab.fkream.utils.SessionUtil;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.SendResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationMapper notificationMapper;

    private static final String NEW_IMMEDIATE_PURCHASE_PRICE = "신규 즉시 구매가";
    private static final String COMPLETE_PURCHASE_BIDDING = "구매 입찰 완료";
    private static final String COMPLETE_SALE_BIDDING = "판매 입찰 완료";
    private static final String COMPLETE_DEAL = "거래 완료";
    private static final String CANCEL_DEAL = "거래 취소";

    public void save(Notification notification) {
        notificationMapper.save(notification);
    }

    public void saveAll(List<Notification> notifications) {
        notificationMapper.saveAll(notifications);
    }

    public List<Notification> findByUserId(Long userId) {
        return notificationMapper.findByUserId(userId);
    }

    public Notification findById(Long id) {
        Notification notification = notificationMapper.findById(id);
        if (notification != null) {
            throw new NoDataFoundException();
        }
        if (notification.getUserId() == SessionUtil.getLoginUserId()) {
            return notificationMapper.findById(id);
        }
        throw new NotOwnedDataException();
    }

    public void delete(Long id) {
        findById(id);
        notificationMapper.delete(id);
    }

    public List<String> sendPriceChangeNotification(List<String> tokenList, String itemName) {
        TitleInfo titleInfo = getTitleInfoForPurchasePrice(itemName);
        List<Message> messages = getMessages(tokenList, titleInfo);
        return sendMessages(tokenList, messages);
    }

    public List<String> sendDealStatusChangeNotification(String token, Deal deal) {
        TitleInfo titleInfo = getTitleInfoForDealStatus(deal);
        List<Message> messages = getMessages(List.of(token), titleInfo);
        return sendMessages(List.of(token), messages);
    }

    public TitleInfo getTitleInfoForPurchasePrice(String itemName) {
        return TitleInfo.builder()
            .title(NEW_IMMEDIATE_PURCHASE_PRICE)
            .context(itemName + " : " + NEW_IMMEDIATE_PURCHASE_PRICE + "가 있습니다.").build();
    }

    public TitleInfo getTitleInfoForDealStatus(Deal deal) {
        if (deal.getStatus() == Status.PROGRESS && deal.getDealType() == DealType.SALE) {
            return TitleInfo.builder().title(COMPLETE_SALE_BIDDING)
                .context(makeContextForDeal(deal, COMPLETE_SALE_BIDDING)).build();
        }
        if (deal.getStatus() == Status.PROGRESS && deal.getDealType() == DealType.PURCHASE) {
            return TitleInfo.builder().title(COMPLETE_PURCHASE_BIDDING)
                .context(makeContextForDeal(deal, COMPLETE_PURCHASE_BIDDING)).build();
        }
        if (deal.getStatus() == Status.COMPLETION) {
            return TitleInfo.builder().title(COMPLETE_DEAL)
                .context(makeContextForDeal(deal, COMPLETE_DEAL)).build();
        }
        if (deal.getStatus() == Status.CANCEL) {
            return TitleInfo.builder().title(CANCEL_DEAL)
                .context(makeContextForDeal(deal, CANCEL_DEAL)).build();
        }
        log.error("거래 상태 및 거래 종류 누락");
        throw new RuntimeException("거래 상태 및 거래 종류 누락");
    }


    private String makeContextForDeal(Deal deal, String title) {
        return deal.getItem().getItemName() + " : " + title + " 되었습니다.";
    }

    private List<String> sendMessages(List<String> tokenList, List<Message> messages) {
        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendAll(messages);
            List<String> failedTokens = new ArrayList<>();
            if (response.getFailureCount() > 0) {
                List<SendResponse> responses = response.getResponses();

                for (int i = 0; i < responses.size(); i++) {
                    if (!responses.get(i).isSuccessful()) {
                        failedTokens.add(tokenList.get(i));
                    }
                }
                log.error("유효하지 않은 토큰 : {}" + failedTokens);
            }
            return failedTokens;
        } catch (FirebaseMessagingException e) {
            log.error("푸쉬 알림을 보낼 수 없습니다. : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private List<Message> getMessages(List<String> tokenList, TitleInfo titleInfo) {
        return tokenList.stream().map(token -> Message.builder()
            .putData("time", LocalDateTime.now().toString())
            .putData("title", titleInfo.getTitle())
            .putData("content", titleInfo.getContext())
            .setToken(token)
            .build()).collect(Collectors.toList());
    }


}
