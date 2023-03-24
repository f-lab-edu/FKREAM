package com.flab.fkream.login;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flab.fkream.users.UsersService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
public class LoginController {
	private final UsersService usersService;

	@PostMapping("/login")
	public void login(@RequestBody LoginForm) {

	}
}
