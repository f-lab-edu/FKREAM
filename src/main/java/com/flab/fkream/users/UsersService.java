package com.flab.fkream.users;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersService {
	private final UsersMapper usersMapper;

	public Long addUser(Users users) {
		usersMapper.save(users);
		return users.getId();
	}

	public Users login(String email, String password) {
		return usersMapper.findOne(email, password);
	}
}
