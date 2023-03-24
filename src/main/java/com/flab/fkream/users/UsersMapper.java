package com.flab.fkream.users;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UsersMapper {

	void save(Users users);

	Users findOne(Long id);

	Users findOne(String email, String password);

}
