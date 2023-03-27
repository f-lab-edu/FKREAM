package com.flab.fkream.users;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.BDDMockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.flab.fkream.login.LoginForm;

@SpringBootTest
@AutoConfigureMockMvc
class UsersServiceTest {
	@InjectMocks
	UsersService usersService;

	@Mock
	UsersMapper usersMapper;

	@Test
	void login() {
		Users user = Users.builder()
				.email("test1")
				.password("000")
				.gender("aa")
				.name("test")
				.adAgreement(true)
				.fourteenAgreement(true)
				.personalAuthentication(true)
				.phoneNumber("010")
				.build();

		given(usersMapper.findByLoginForm(any())).willReturn(user);
		Users login = usersService.login(new LoginForm(user.getEmail(), user.getPassword()));
		String email = login.getEmail();
		Assertions.assertThat(email).isEqualTo(user.getEmail());
	}

	@Test
	void addUsers(){
		Users user = Users.builder()
				.id(1L)
				.email("test1")
				.password("000")
				.gender("aa")
				.name("test")
				.adAgreement(true)
				.fourteenAgreement(true)
				.personalAuthentication(true)
				.phoneNumber("010")
				.build();

		doNothing().when(usersMapper).save(any());
		usersService.addUser(user);
	}

	@Test
	void duplicatedEmailTest(){
		Users user = Users.builder()
				.id(1L)
				.email("test1")
				.password("000")
				.gender("aa")
				.name("test")
				.adAgreement(true)
				.fourteenAgreement(true)
				.personalAuthentication(true)
				.phoneNumber("010")
				.build();

		given(usersMapper.emailCheck(user.getEmail())).willReturn(1);
		Assertions.assertThatThrownBy(() -> usersService.addUser(user)).isInstanceOf(RuntimeException.class);
	}
}