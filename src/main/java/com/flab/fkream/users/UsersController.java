package com.flab.fkream.users;

import com.flab.fkream.aop.LoginCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Log4j2
public class UsersController {

	private final UsersService usersService;

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public void signUp(@RequestBody @NotNull Users user) {
		if(user.hasNullData()){
			throw new NullPointerException("회원가입시 필수 데이터를 모두 입력해야 합니다.");
		}
		usersService.addUser(user);
		if(user.getId()==null){

		}
	}

	@GetMapping("/test")
	@ResponseStatus(HttpStatus.OK)
	@LoginCheck(type = LoginCheck.UserType.USER)
	public void test(){

	}

}

