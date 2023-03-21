package com.flab.fkream.brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BrandService {
	@Autowired
	private BrandMapper brandMapper;

	@Transactional(rollbackFor = RuntimeException.class)
	public Long addBrand(Brand brandInfo) {
		Long brandId = brandMapper.insertBrand(brandInfo);
		if (brandId == null) {
			log.error("insert brand error! brandInfo : {}", brandInfo);
			throw new NullPointerException("insert brand error!" + brandInfo);
		}
		return brandId;
	}
}
