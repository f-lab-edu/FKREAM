package com.flab.fkream.salesAccount;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SalesAccountMapper {

    void save(SalesAccount salesAccount);
}
