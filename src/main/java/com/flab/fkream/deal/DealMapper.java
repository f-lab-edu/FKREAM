package com.flab.fkream.deal;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DealMapper {

    void save(Deal deal);

}
