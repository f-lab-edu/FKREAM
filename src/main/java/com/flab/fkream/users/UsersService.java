package com.flab.fkream.users;

import com.flab.fkream.error.exception.DuplicateEmailException;
import com.flab.fkream.login.LoginForm;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersService {
	private final UsersMapper usersMapper;

	public Long addUser(Users users) {

		if(isDuplicatedEmail(users.getEmail())){
			throw new DuplicateEmailException("존재하는 이메일 입니다.");
		}
		usersMapper.save(users);
		return users.getId();
	}

	public boolean isDuplicatedEmail(String email) {
		return usersMapper.emailCheck(email) == 1;
	}

	public Users login(LoginForm loginForm) {
		return usersMapper.findByLoginForm(loginForm);
	}
}
