package com.flab.fkream.myItems;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyItemMapper {

    int save(MyItem myItem);

    MyItem findOne(Long id);

    List<MyItem> findAllByUserId(Long userId);

    int update(MyItem myItem);

    int delete(Long id);
}
