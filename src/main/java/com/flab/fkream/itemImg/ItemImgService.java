package com.flab.fkream.itemImg;

import java.util.List;

import com.flab.fkream.error.exception.MapperException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ItemImgService {
  private final ItemImgMapper itemImgMapper;

  public void addItemImg(ItemImg itemImgInfo) {
    itemImgInfo.setCreatedAt();
    int result = itemImgMapper.save(itemImgInfo);
    if (result != 1) {
      log.error("insert ItemImg error! itemImgInfo : {}", itemImgInfo);
      throw new MapperException("insert itemImg error!" + itemImgInfo);
    }
  }

  public List<ItemImg> findImagesByItemId(Long itemId) {
    List<ItemImg> itemImages = itemImgMapper.findImagesByItemId(itemId);
    if (itemImages == null) {
      log.error("find images by ItemId error itemId : {}", itemId);
      throw new MapperException("find images by ItemId error itemId :" + itemId);
    }
    return itemImages;
  }

  public void delete(Long id) {
    int result = itemImgMapper.delete(id);
    if (result != 1) {
      log.error("delete itemImg error!");
      throw new MapperException("delete ItemImg error");
    }
  }
}
