package com.flab.fkream.manager;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ManagerService {
	private final ManagerMapper managerMapper;

	@Transactional(rollbackFor = RuntimeException.class)
	public Long addManager(Manager managerInfo) {
		Long managerId = managerMapper.save(managerInfo);
		if (managerId == null) {
			log.error("insert manager error! managerInfo : {}", managerInfo);
			throw new NullPointerException("insert manager error!" + managerInfo);
		}
		return managerId;
	}

	public Manager findOne(Long managerId) {
		return managerMapper.findOne(managerId);
	}

	public List<Manager> findAll() {
		return managerMapper.findAll();
	}
}
