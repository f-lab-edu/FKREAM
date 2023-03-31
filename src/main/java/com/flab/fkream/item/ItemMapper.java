package com.flab.fkream.item;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemMapper {
  int save(Item item);

  Item findOne(Long id);

  List<Item> findAll();

  int update(Item itemInfo);

  int delete(Long id);
}
