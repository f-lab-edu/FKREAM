package com.flab.fkream.manager;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ManagerMapper {
	Long save(Manager manager);

	Manager findOne(Long id);

	List<Manager> findAll();
}
