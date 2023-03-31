package com.flab.fkream.salesAccount;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class SalesAccountService {
	private final SalesAccountMapper salesAccountMapper;

	public int save(SalesAccount salesAccount) {
		return salesAccountMapper.save(salesAccount);
	}

	public SalesAccount findById(Long id) {
		return salesAccountMapper.findById(id);
	}

	public List<SalesAccount> findAll() {
		return salesAccountMapper.findAll();
	}

	public int update(SalesAccount salesAccount) {
		return salesAccountMapper.update(salesAccount);
	}

	public int deleteById(Long id) {
		return salesAccountMapper.deleteById(id);
	}
}
