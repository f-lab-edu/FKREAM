package com.flab.fkream.mapper;

import com.flab.fkream.login.LoginForm;
import com.flab.fkream.user.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

  void save(User user);

  User findByEmail(String email);

  User findOne(Long id);

}
