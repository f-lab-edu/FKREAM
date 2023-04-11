package com.flab.fkream.brand;

import com.flab.fkream.error.exception.NoDataFoundException;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class BrandService {

    private final BrandMapper brandMapper;

    public void addBrand(Brand brandInfo) {
        brandMapper.save(brandInfo);
    }

    public Brand findOne(Long brandId) {
        Brand brand = brandMapper.findOne(brandId);
        if (brand == null) {
            throw new NoDataFoundException();
        }
        return brand;
    }

    public Brand findByBrandName(String brandName){
        Brand brand = brandMapper.findByBrandName(brandName);
        if (brand == null) {
            throw new NoDataFoundException();
        }
        return brand;
    }

    public List<Brand> findAll() {
        List<Brand> brands = brandMapper.findAll();
        if (brands.size() == 0) {
            throw new NoDataFoundException();
        }
        return brands;
    }

    public void update(Brand brandInfo) {
        brandMapper.update(brandInfo);
        brandMapper.findOne(brandInfo.getId());
    }

    public void delete(Long id) {
        brandMapper.delete(id);
    }
}
