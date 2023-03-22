package com.flab.fkream.brand;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/brand")
public class BrandController {
	private final BrandService brandService;

	@PostMapping("")
	public HttpStatus addBrand(@RequestBody Brand brandInfo) {
		brandService.addBrand(brandInfo);
		return HttpStatus.CREATED;
	}
}
