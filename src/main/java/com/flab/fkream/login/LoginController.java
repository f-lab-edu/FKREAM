package com.flab.fkream.login;

import static com.flab.fkream.aop.LoginCheck.*;

import com.flab.fkream.aop.LoginCheck;
import com.flab.fkream.users.Users;
import com.flab.fkream.utils.SessionUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.flab.fkream.users.UsersService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@Log4j2
public class LoginController {
	private final UsersService usersService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginForm loginForm, HttpSession httpSession) {
		Users user = usersService.login(loginForm);
		LoginResponse loginResponse;
		ResponseEntity<LoginResponse> responseEntity = null;
		if (user == null) {
			loginResponse = LoginResponse.FAIL;
			return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.UNAUTHORIZED);
		}

		loginResponse = LoginResponse.success(user);
		SessionUtil.setLoginUserId(httpSession, user.getId());
		return new ResponseEntity<>(loginResponse, HttpStatus.OK);
	}

	@GetMapping("/logout")
	@LoginCheck(type = UserType.USER)
	public void logout(HttpSession session) {
		SessionUtil.logoutUser(session);
	}

	@Getter
	@AllArgsConstructor
	@RequiredArgsConstructor
	private static class LoginResponse {
		enum LoginStatus {
			SUCCESS, FAIL, DELETED
		}

		@NotNull
		private LoginStatus result;
		private Users user;

		private static final LoginResponse FAIL = new LoginResponse(LoginStatus.FAIL, null);

		private static LoginResponse success(Users users) {
			return new LoginResponse(LoginStatus.SUCCESS, users);
		}
	}
}
