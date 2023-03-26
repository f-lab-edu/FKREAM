package com.flab.fkream.brand;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class BrandService {
	private final BrandMapper brandMapper;

	@Transactional(rollbackFor = RuntimeException.class)
	public Long addBrand(Brand brandInfo) {
		Long brandId = brandMapper.save(brandInfo);
		if (brandId == null) {
			log.error("insert brand error! brandInfo : {}", brandInfo);
			throw new NullPointerException("insert brand error!" + brandInfo);
		}
		return brandId;
	}

	public Brand findOne(Long brandId) {
		return brandMapper.findOne(brandId);
	}

	public List<Brand> findAll() {
		return brandMapper.findAll();
	}
}
