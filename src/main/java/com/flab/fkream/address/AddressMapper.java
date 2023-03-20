package com.flab.fkream.address;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AddressMapper {

    void save(Address address);
}
