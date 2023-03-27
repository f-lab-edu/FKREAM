package com.flab.fkream.brand;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class BrandServiceTest {
	@Autowired
	BrandService brandService;

	@Test
	void 브랜드_추가() throws Exception {
		//given
		Brand brand = Brand.builder()
			.brandName("Nike")
			// .luxury("n")
			.build();

		//when
		Long brandId = brandService.addBrand(brand);
		//then
		System.out.println("brand.getId() = " + brand.getId());
		System.out.println("brandId = " + brandId);

	}

	@Test
	void 브랜드_조회() throws Exception {
		//given
		Brand brand = Brand.builder()
			.brandName("Nike")
			// .luxury("n")
			.build();
		Long brandId = brandService.addBrand(brand);
		System.out.println("brand.getId() = " + brand.getId());
		System.out.println("brandId = " + brandId);

		//when
		Brand findBrand = brandService.findOne(brandId);

		//then
		System.out.println("findBrand.getId() = " + findBrand.getId());
		Assertions.assertThat(findBrand.getId()).isEqualTo(brandId);

	}

}