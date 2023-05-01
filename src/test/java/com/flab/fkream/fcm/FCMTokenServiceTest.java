package com.flab.fkream.fcm;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FCMTokenServiceTest {


    @Mock
    FCMTokenMapper fcmTokenMapper;

    @InjectMocks
    FCMTokenService fcmTokenService;

    @Test
    void saveToken() {
        given(fcmTokenMapper.save(1L, "token")).willReturn(1);

        fcmTokenService.saveToken(1L, "token");

        then(fcmTokenMapper).should().save(1L, "token");
    }

    @Test
    void findToken() {
        given(fcmTokenMapper.findByUserId(1L)).willReturn("token");

        String token = fcmTokenService.findToken(1L);

        assertThat(token).isEqualTo("token");
    }

    @Test
    void deleteToken() {
        given(fcmTokenMapper.delete(1L)).willReturn(1);

        fcmTokenService.deleteToken(1L);

        then(fcmTokenMapper).should().delete(1L);
    }

}