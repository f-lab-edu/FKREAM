package com.flab.fkream.address;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface AddressMapper {

    int save(Address address);

    @Transactional(readOnly = true)
    Address findOne(Long id);

    List<Address> findByUserId(Long userId);

    int update(Address addressInfo);

    int delete(Long id);
}
