package com.flab.fkream.address;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AddressMapper {

    String insert = "INSERT INTO ADDRESS(user_id, name, phone_number, zipcode, detail1, detail2, default_address, created_at) " +
            "VALUES ( #{address.users.id}, #{address.name}, #{address.phoneNumber}, #{address.zipcode}, #{address.detail1}, #{address.detail2}, " +
            "#{address.defaultAddress}, #{address.createdAt})";
    void save(Address address);
}
