package com.flab.fkream.notification;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.flab.fkream.deal.Deal;
import com.flab.fkream.deal.Status;
import com.flab.fkream.item.Item;
import com.flab.fkream.utils.SessionUtil;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.FirebaseOptions.Builder;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.SendResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    NotificationMapper notificationMapper;

    @Mock
    FirebaseMessaging firebaseMessaging;

    @InjectMocks
    NotificationService notificationService;

    static MockedStatic<SessionUtil> sessionUtilities;

    @BeforeAll
    public static void beforeAll() {
        sessionUtilities = Mockito.mockStatic(SessionUtil.class);
    }

    @AfterAll
    public static void afterAll() {
        sessionUtilities.close();
    }

    Notification notification = Notification.builder().title("test").id(1L).userId(1L).build();
    Deal deal = Deal.builder().item(Item.builder().itemName("testName").build()).status(Status.COMPLETION).build();


    @Test
    void save() {
        given(notificationMapper.save(notification)).willReturn(1);
        notificationService.save(notification);
        then(notificationMapper).should().save(notification);
    }

    @Test
    void saveAll() {
        given(notificationMapper.saveAll(List.of(notification))).willReturn(1);
        notificationService.saveAll(List.of(notification));
        then(notificationMapper).should().saveAll(List.of(notification));
    }

    @Test
    void findByUserId() {
        given(notificationMapper.findByUserId(1L)).willReturn(List.of(notification));
        List<Notification> notifications = notificationService.findByUserId(1L);
        assertThat(notifications).contains(notification);
    }

    @Test
    void findById() {
        given(notificationMapper.findById(1L)).willReturn(notification);
        sessionUtilities.when(SessionUtil::getLoginUserId).thenReturn(1L);
        Notification notificationInfo = notificationService.findById(1L);
        assertThat(notificationInfo).isEqualTo(notificationInfo);
    }

    @Test
    void delete() {
        given(notificationMapper.delete(1L)).willReturn(1);
        given(notificationMapper.findById(1L)).willReturn(notification);
        sessionUtilities.when(SessionUtil::getLoginUserId).thenReturn(1L);
        notificationService.delete(1L);
        then(notificationMapper).should().delete(1L);
    }

    @Test
    void sendPriceChangeNotification() throws FirebaseMessagingException {
        // Given
        List<String> tokenList = Arrays.asList("token1", "token2", "token3");
        String itemName = "TestItem";

        BatchResponse batchResponse = mock(BatchResponse.class);
        SendResponse successfulResponse = mock(SendResponse.class);
        SendResponse failedResponse = mock(SendResponse.class);

        when(successfulResponse.isSuccessful()).thenReturn(true);
        when(failedResponse.isSuccessful()).thenReturn(false);
        when(batchResponse.getResponses()).thenReturn(
            Arrays.asList(successfulResponse, successfulResponse, failedResponse));
        when(batchResponse.getFailureCount()).thenReturn(1);
        when(firebaseMessaging.sendAll(anyList())).thenReturn(batchResponse);

        // When
        List<String> failedTokens = notificationService.sendPriceChangeNotification(tokenList,
            itemName);

        // Then
        assertEquals(1, failedTokens.size());
        assertEquals("token3", failedTokens.get(0));
    }

    @Test
    void sendDealStatusChangeNotification() throws FirebaseMessagingException {
        String tokenInfo = "testToken";

        BatchResponse batchResponse = mock(BatchResponse.class);
        when(batchResponse.getFailureCount()).thenReturn(0);
        when(firebaseMessaging.sendAll(anyList())).thenReturn(batchResponse);

        List<String> failedTokens = notificationService.sendDealStatusChangeNotification(tokenInfo,
            deal);
        assertEquals(0, failedTokens.size());
    }

    @Test
    void getTitleInfoForPurchasePrice() {
        assertThat(notificationService.getTitleInfoForPurchasePrice("nike").getTitle()).isEqualTo(
            "신규 즉시 구매가");
    }

    @Test
    void getTitleInfoForDealStatus() {
        assertThat(notificationService.getTitleInfoForDealStatus(deal).getTitle()).isEqualTo(
            "거래 완료");
    }
}