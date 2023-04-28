package com.flab.fkream.paymentCard;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.flab.fkream.utils.SessionUtil;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentCardServiceTest {

    @Mock
    PaymentCardMapper paymentCardMapper;

    @InjectMocks
    PaymentCardService paymentCardService;

    static MockedStatic<SessionUtil> sessionUtilities;

    PaymentCard paymentCardInfo = PaymentCard.builder()
        .userId(1L)
        .cardCompany("woori")
        .cardNumber("1111")
        .expiration("12/32")
        .cardPw("1234")
        .build();

    @BeforeAll
    public static void beforeAll(){
        sessionUtilities = Mockito.mockStatic(SessionUtil.class);
    }
    @AfterAll
    public static void afterAll(){
        sessionUtilities.close();
    }
    @Test
    void save() {
        given(paymentCardMapper.save(paymentCardInfo)).willReturn(1);
        paymentCardService.save(paymentCardInfo);
        then(paymentCardMapper).should().save(paymentCardInfo);
    }

    @Test
    void findByUserId() {
        given(paymentCardMapper.findByUserId(paymentCardInfo.getUserId())).willReturn(
            List.of(paymentCardInfo));
        given(SessionUtil.getLoginUserId()).willReturn(paymentCardInfo.getUserId());
        assertThat(paymentCardService.findByUserId()).contains(
            paymentCardInfo);
    }

    @Test
    void findById() {
        given(paymentCardMapper.findById(1L)).willReturn(paymentCardInfo);
        sessionUtilities.when(SessionUtil::getLoginUserId).thenReturn(1L);
        assertThat(paymentCardService.findById(1L)).isEqualTo(paymentCardInfo);
    }

    @Test
    void deleteById() {
        given(paymentCardMapper.delete(1L)).willReturn(1);
        given(paymentCardMapper.findById(1L)).willReturn(paymentCardInfo);
        sessionUtilities.when(SessionUtil::getLoginUserId).thenReturn(1L);
        paymentCardService.deleteById(1L);
        then(paymentCardMapper).should().delete(1L);
    }
}