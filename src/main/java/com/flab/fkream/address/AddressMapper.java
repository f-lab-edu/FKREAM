package com.flab.fkream.address;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface AddressMapper {

    int save(Address address);

    @Transactional(readOnly = true)
    Address findOne(Long id);

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "AddressByUserId", key = "#p0")
    List<Address> findByUserId(Long userId);

    @CacheEvict(cacheNames = "AddressByUserId", key = "#p0.userId")
    int update(Address addressInfo);

    int delete(Long id);
}
