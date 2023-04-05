package com.flab.fkream.brand;

import java.util.List;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping("/brands")
    public HttpStatus addBrand(@Valid @RequestBody Brand brandInfo) {
        brandService.addBrand(brandInfo);
        return HttpStatus.CREATED;
    }

    @GetMapping("/brands")
    public List<Brand> findAll() {
        return brandService.findAll();
    }

    @GetMapping("/brands/{id}")
    public Brand findOne(@PathVariable Long id) {
        return brandService.findOne(id);
    }

    @PatchMapping("/brands/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@Valid @RequestBody Brand brandInfo) {
        brandService.update(brandInfo);
    }

    @DeleteMapping("/brands/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        brandService.delete(id);
    }
}
