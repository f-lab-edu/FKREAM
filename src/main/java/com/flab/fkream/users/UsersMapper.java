package com.flab.fkream.users;

import com.flab.fkream.login.LoginForm;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UsersMapper {

	void save(Users users);

	Users findByLoginForm(LoginForm loginForm);

	int emailCheck(String email);
}
