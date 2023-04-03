package com.flab.fkream.brand;

import com.flab.fkream.error.exception.NoDataFoundException;
import com.flab.fkream.item.Item;
import java.util.List;

import com.flab.fkream.error.exception.MapperException;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
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
        try{
            brandMapper.save(brandInfo);
        }catch (DataAccessException e){
            log.error("[BrandService.addBrand] insert brand error! brandInfo : {}", brandInfo);
            throw new MapperException(e);
        }
    }

    public Brand findOne(Long brandId) {
        try{
            Brand brand = brandMapper.findOne(brandId);
            if (brand == null) {
                throw new NoDataFoundException();
            }
            return brand;
        }catch (DataAccessException e){
            log.error("[BrandService.findOne] find brand by id error!");
            throw new MapperException(e);
        }
    }

    public List<Brand> findAll() {
        try{
            List<Brand> brands = brandMapper.findAll();
            if (brands.size()==0) {
                throw new NoDataFoundException();
            }
            return brands;
        }catch (DataAccessException e){
            log.error("[BrandService.findAll] find all brand error!}");
            throw new MapperException(e);
        }

    }

    public void update(Brand brandInfo) {
        try{
            brandMapper.update(brandInfo);
            brandMapper.findOne(brandInfo.getId());
        }catch (DataAccessException e){
            log.error("[BrandService.update] update brand error! brandInfo : {}", brandInfo);
            throw new MapperException(e);
        }
    }

    public void delete(Long id) {
        try{
            brandMapper.delete(id);
        }catch (DataAccessException e){
            log.error("[BrandService.delete] delete brand error!");
            throw new MapperException(e);
        }
    }
}
