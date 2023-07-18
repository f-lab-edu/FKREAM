package com.flab.fkream.brand;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    @PostMapping("")
    public void addBrand(@Valid @RequestBody Brand brandInfo) {
        brandService.addBrand(brandInfo);
    }

    @GetMapping("")
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
