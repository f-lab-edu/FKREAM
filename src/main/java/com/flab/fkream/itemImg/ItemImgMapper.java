package com.flab.fkream.itemImg;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;

@Mapper
public interface ItemImgMapper {

    int save(ItemImg itemImg);

    @Cacheable(cacheNames = "ItemImg", key = "#p0")
    List<ItemImg> findImagesByItemId(Long itemId);

    int delete(Long id);
}
