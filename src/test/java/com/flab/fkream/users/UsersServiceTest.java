package com.flab.fkream.users;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.flab.fkream.login.LoginForm;

@SpringBootTest
class UsersServiceTest {
	@Autowired
	UsersService usersService;

	@Test
	@Transactional
	void login() {
		Users user = Users.builder().email("test@gmail.com").password("0000").build();
		usersService.addUser(user);

		Long loginId = usersService.login(new LoginForm(user.getEmail(), user.getPassword())).getId();

		Assertions.assertThat(loginId).isEqualTo(user.getId());
	}
}