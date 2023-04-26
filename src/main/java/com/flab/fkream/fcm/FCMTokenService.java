package com.flab.fkream.fcm;

import com.flab.fkream.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class FCMTokenService {

    private final FCMTokenMapper fcmTokenMapper;

    public void saveToken(Long userId, String token) {
        fcmTokenMapper.save(userId, token);
    }

    public String findToken(Long userId) {
        return fcmTokenMapper.findByUserId(userId);
    }

    public void deleteToken(Long userId) {
        fcmTokenMapper.delete(userId);
    }
}
