package com.flab.fkream.brand;

import java.util.List;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    @PostMapping("")
    public void addBrand(@Valid @RequestBody Brand brandInfo) {
        brandService.addBrand(brandInfo);
    }

    @GetMapping("/brands")
    public List<Brand> findAll() {
        return brandService.findAll();
    }

    @GetMapping("/{id}")
    public Brand findOne(@PathVariable Long id) {
        return brandService.findOne(id);
    }

    @PatchMapping("/{id}")
    public void update(@Valid @RequestBody Brand brandInfo) {
        brandService.update(brandInfo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        brandService.delete(id);
    }
}
