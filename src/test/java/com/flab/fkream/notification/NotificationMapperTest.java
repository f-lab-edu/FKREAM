package com.flab.fkream.notification;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NotificationMapperTest {

    @Autowired
    NotificationMapper notificationMapper;

    Notification notification = Notification.builder().title("test").userId(1L).build();

    @Test
    void save() {
        assertThat(notificationMapper.save(notification)).isEqualTo(1);
    }

    @Test
    void saveAll() {
        assertThat(notificationMapper.saveAll(List.of(notification))).isEqualTo(1);
    }

    @Test
    void findByUserId() {
        notificationMapper.save(notification);
        assertThat(notificationMapper.findByUserId(1L)).contains(notification);
    }

    @Test
    void findById() {
        notificationMapper.save(notification);
        assertThat(notificationMapper.findById(notification.getId())).isEqualTo(notification);
    }

    @Test
    void delete() {
        notificationMapper.save(notification);
        assertThat(notificationMapper.delete(notification.getId())).isEqualTo(1);
    }
}