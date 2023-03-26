package com.flab.fkream.users;

import com.flab.fkream.error.exception.DuplicateEmailException;
import com.flab.fkream.login.LoginForm;
import com.flab.fkream.utils.SHA256Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Log4j2
@RequiredArgsConstructor
public class UsersService {
	private final UsersMapper usersMapper;

	public void addUser(Users users) {

		if(isDuplicatedEmail(users.getEmail())){
			throw new DuplicateEmailException("존재하는 이메일 입니다.");
		}
		users.setPassword(SHA256Util.encrypt(users.getPassword()));
		users.setCreatedAt(LocalDateTime.now());
		usersMapper.save(users);
		if(users.getId()==null){
			log.error("addUser Error{}", users);
			throw new RuntimeException("addUser Error, 회원가입 메소드 확인");
		}
	}

	private boolean isDuplicatedEmail(String email) {
		return usersMapper.emailCheck(email) == 1;
	}

	public Users login(LoginForm loginForm) {
		loginForm.setPassword(SHA256Util.encrypt(loginForm.getPassword()));
		return usersMapper.findByLoginForm(loginForm);
	}
}
