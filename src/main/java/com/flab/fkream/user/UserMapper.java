package com.flab.fkream.user;

import com.flab.fkream.login.LoginForm;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

  void save(User user);

  User findByEmail(String email);

}
