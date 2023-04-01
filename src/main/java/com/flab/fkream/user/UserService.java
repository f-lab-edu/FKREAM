package com.flab.fkream.user;

import com.flab.fkream.error.exception.DuplicatedEmailException;
import com.flab.fkream.error.exception.LoginFailureException;
import com.flab.fkream.error.exception.SignUpFailureException;
import com.flab.fkream.login.LoginForm;
import com.flab.fkream.utils.SHA256Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserService {
  private final UserMapper userMapper;

  public void addUser(User user) {
    isDuplicatedEmail(user.getEmail());
    user.setPassword(SHA256Util.encrypt(user.getPassword()));
    user.setCreatedAt(LocalDateTime.now());
    try {
      userMapper.save(user);
    } catch (Exception e) {
      log.error("userMapper.save() 에러");
      throw new SignUpFailureException();
    }
  }

  private void isDuplicatedEmail(String email) {
    User user = userMapper.findByEmail(email);
    if (user != null) throw new DuplicatedEmailException();
  }

  public User login(LoginForm loginForm) {
    loginForm.setPassword(SHA256Util.encrypt(loginForm.getPassword()));
    User user = userMapper.findByEmail(loginForm.getEmail());
    if (user == null) {
      throw new LoginFailureException();
    }
    if (user.getPassword().equals(loginForm.getPassword())){
      return user;
    }
    throw new LoginFailureException();
  }
}
