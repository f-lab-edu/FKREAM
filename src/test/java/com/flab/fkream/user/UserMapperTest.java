package com.flab.fkream.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.fkream.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles({"test"})
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    User user = User.builder()
        .email("test1")
        .password("000")
        .gender("aa")
        .name("test")
        .adAgreement(true)
        .fourteenAgreement(true)
        .personalAuthentication(true)
        .phoneNumber("010")
        .build();

    @Test
    void save() {
        userMapper.save(user);
        assertThat(user.getId()).isNotNull();
    }

    @Test
    void findByEmail() {
        userMapper.save(user);
        User userFound = userMapper.findByEmail(user.getEmail());
        assertThat(user).isEqualTo(userFound);
    }
}