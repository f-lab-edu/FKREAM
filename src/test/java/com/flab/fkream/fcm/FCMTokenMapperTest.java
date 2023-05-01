package com.flab.fkream.fcm;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FCMTokenMapperTest {

    @Autowired
    FCMTokenMapper fcmTokenMapper;

    @Test
    void save() {
        assertThat(fcmTokenMapper.save(1L, "token")).isEqualTo(1);
    }

    @Test
    void findByUserId() {
        fcmTokenMapper.save(1L, "token");
        assertThat(fcmTokenMapper.findByUserId(1L)).isEqualTo("token");
    }

    @Test
    void delete() {
        fcmTokenMapper.save(1L, "token");
        assertThat(fcmTokenMapper.delete(1L)).isEqualTo(1);
    }
}