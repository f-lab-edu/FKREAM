package com.flab.fkream.brand;

import java.util.List;

import com.flab.fkream.error.exception.MapperException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class BrandService {
  private final BrandMapper brandMapper;

  public void addBrand(Brand brandInfo) {
    int result = brandMapper.save(brandInfo);
    if (result != 1) {
      log.error("insert brand error! brandInfo : {}", brandInfo);
      throw new MapperException("insert brand error!" + brandInfo);
    }
  }

  public Brand findOne(Long brandId) {
    Brand brand = brandMapper.findOne(brandId);
    if (brand == null) {
      log.error("find brand error! brandId : {}", brandId);
      throw new MapperException("find brand error! brandId :" + brandId);
    }
    return brand;
  }

  public List<Brand> findAll() {
    List<Brand> brands = brandMapper.findAll();
    if (brands == null) {
      log.error("find all brand error!");
      throw new MapperException("find all brand error!");
    }
    return brands;
  }

  public void update(Brand brandInfo) {
    int result = brandMapper.update(brandInfo);
    if (result != 1) {
      log.error("update brand error! {}", brandInfo);
      throw new MapperException("update brand error");
    }
  }

  public void delete(Long id) {
    int result = brandMapper.delete(id);
    if (result != 1) {
      log.error("delete brand error!");
      throw new MapperException("delete brand error");
    }
  }
}
