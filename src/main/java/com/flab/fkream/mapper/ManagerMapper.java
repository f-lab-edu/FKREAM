package com.flab.fkream.mapper;

import com.flab.fkream.manager.Manager;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ManagerMapper {
	Long save(Manager manager);

	Manager findOne(Long id);

	List<Manager> findAll();
}
