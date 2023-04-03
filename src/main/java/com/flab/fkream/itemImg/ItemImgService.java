package com.flab.fkream.itemImg;

import com.flab.fkream.error.exception.NoDataFoundException;
import java.util.List;

import com.flab.fkream.error.exception.MapperException;
import org.springframework.dao.DataAccessException;
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
        try {
            itemImgInfo.setCreatedAt();
            itemImgMapper.save(itemImgInfo);
        } catch (DataAccessException e) {
            log.error("[ItemImgService.addItemImg] insert itemImg error! itemImgInfo : {}",
                itemImgInfo);
            throw new MapperException(e);
        }
    }

    public List<ItemImg> findImagesByItemId(Long itemId) {
        try {
            List<ItemImg> itemImages = itemImgMapper.findImagesByItemId(itemId);
            if (itemImages.size() == 0) {
                throw new NoDataFoundException();
            }
            return itemImages;
        } catch (DataAccessException e) {
            log.error("[ItemImgService.findImagesByItemId] find itemImg error!");
            throw new MapperException(e);
        }
    }

    public void delete(Long id) {
        try {
            itemImgMapper.delete(id);
        } catch (DataAccessException e) {
            log.error("[ItemImgService.delete] delete itemImg error!");
            throw new MapperException(e);
        }
    }
}
