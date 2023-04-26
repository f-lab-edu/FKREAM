package com.flab.fkream.login;

import static com.flab.fkream.aop.LoginCheck.*;

import com.flab.fkream.aop.LoginCheck;
import com.flab.fkream.error.exception.LoginFailureException;
import com.flab.fkream.fcm.FCMTokenService;
import com.flab.fkream.user.User;
import com.flab.fkream.utils.SessionUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flab.fkream.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@Log4j2
public class LoginController {
  private final UserService userService;
  private final FCMTokenService fcmTokenService;

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public void login(@RequestBody LoginForm loginForm, HttpSession httpSession) {
    User user = userService.login(loginForm);
    fcmTokenService.saveToken(user.getId(), loginForm.getFcmToken());
    SessionUtil.setLoginUserId(httpSession, user.getId());
  }

  @GetMapping("/logout")
  @ResponseStatus(HttpStatus.OK)
  @LoginCheck(type = UserType.USER)
  public void logout(HttpSession session) {
    fcmTokenService.deleteToken(SessionUtil.getLoginUserId());
    SessionUtil.logoutUser(session);
  }
}
