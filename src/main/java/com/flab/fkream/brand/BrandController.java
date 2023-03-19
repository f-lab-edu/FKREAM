package com.flab.fkream.brand;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/brand")
public class BrandController {
	private final BrandMapper brandMapper;

	@Autowired
	public BrandController(BrandMapper brandMapper) {
		this.brandMapper = brandMapper;
	}

	@PostMapping("")
	public Brand post(@RequestBody Brand brand) {
		brandMapper.insert(brand);
		return brand;
	}

	@GetMapping("/{brandName}")
	public Brand findByBrandName(@PathVariable("brandName") String brandName) {
		return brandMapper.getByBrandName(brandName);
	}

	@GetMapping("")
	public List<Brand> getAll() {
		return brandMapper.getAll();
	}

}
