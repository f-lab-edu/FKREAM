package com.flab.fkream.brand;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BrandServiceTest {
	@Autowired
	BrandService brandService;

	@Test
	void 브랜드_추가() throws Exception {
		//given
		Brand brand = Brand.builder()
			.brandName("Adidas")
			.luxury("n")
			.build();
		//when
		brandService.addBrand(brand);
		//then

	}

}