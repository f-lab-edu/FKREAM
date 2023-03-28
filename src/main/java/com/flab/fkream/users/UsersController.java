package com.flab.fkream.users;

import com.flab.fkream.aop.LoginCheck;
import com.flab.fkream.error.exception.SignUpException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Log4j2
public class UsersController {

	private final UsersService usersService;

	@PostMapping("")
	public ResponseEntity signUp(@RequestBody @NotNull Users user) {
		if(user.hasNullData()){
			return new ResponseEntity("회원가입시 필수 데이터를 모두 입력해야 합니다.",HttpStatus.BAD_REQUEST);
		}
		usersService.addUser(user);
		if(user.getId()==null){
			log.debug("userService.addUser 에러");
			throw new SignUpException("회원가입 중 문제가 발생했습니다.");
		}
		return new ResponseEntity(HttpStatus.CREATED);
	}

	@GetMapping("/test")
	@ResponseStatus(HttpStatus.OK)
	@LoginCheck(type = LoginCheck.UserType.USER)
	public void test(){

	}

}

