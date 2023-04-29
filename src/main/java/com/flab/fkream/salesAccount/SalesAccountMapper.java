package com.flab.fkream.salesAccount;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SalesAccountMapper {

    int save(SalesAccount salesAccount);

    SalesAccount findByUserId(Long userId);

    List<SalesAccount> findAll();

    int update(SalesAccount salesAccount);

    int deleteByUserId(Long userId);
}
