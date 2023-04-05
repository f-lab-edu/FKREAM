package com.flab.fkream.user;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.flab.fkream.error.exception.DuplicatedEmailException;
import com.flab.fkream.error.exception.LoginFailureException;
import com.flab.fkream.utils.SHA256Util;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.BDDMockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.flab.fkream.login.LoginForm;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
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
    void login() {

        given(userMapper.findByEmail(user.getEmail())).willReturn(User.builder()
            .email("test1")
            .password(SHA256Util.encrypt(user.getPassword()))
            .gender("aa")
            .name("test")
            .adAgreement(true)
            .fourteenAgreement(true)
            .personalAuthentication(true)
            .phoneNumber("010")
            .build());
        User login = userService.login(new LoginForm(user.getEmail(), user.getPassword()));
        String email = login.getEmail();
        Assertions.assertThat(email).isEqualTo(user.getEmail());
    }

    @Test
    void loginFailureTest() {
        given(userMapper.findByEmail(user.getEmail())).willReturn(null);
        Assertions.assertThatThrownBy(
                () -> userService.login(new LoginForm(user.getEmail(), user.getPassword())))
            .isInstanceOf(
                LoginFailureException.class);
    }

    @Test
    void addUsers() {
        doNothing().when(userMapper).save(user);
        given(userMapper.findByEmail(user.getEmail())).willReturn(null);
        userService.addUser(user);
        verify(userMapper, times(1)).save(user);
    }

    @Test
    void duplicatedEmailTest() {
        given(userMapper.findByEmail(user.getEmail())).willReturn(user);
        Assertions.assertThatThrownBy(() -> userService.addUser(user))
            .isInstanceOf(DuplicatedEmailException.class);
    }


}
