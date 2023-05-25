package com.flab.fkream.itemImg;

import com.flab.fkream.error.exception.NoDataFoundException;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ItemImgService {

    private final ItemImgMapper itemImgMapper;

    public void addItemImg(ItemImg itemImgInfo) {
        itemImgInfo.setCreatedAtToNow();
        itemImgMapper.save(itemImgInfo);
    }

    public List<ItemImg> findImagesByItemId(Long itemId) {
        List<ItemImg> itemImages = itemImgMapper.findImagesByItemId(itemId);
        if (itemImages.size() == 0) {
            throw new NoDataFoundException();
        }
        return itemImages;
    }

    public ItemImg findRepresentImageByItemID(Long itemId) {
        List<ItemImg> imagesByItemId = itemImgMapper.findImagesByItemId(itemId);
        for (ItemImg itemImg : imagesByItemId) {
            if (itemImg.isRepresentativeImg()) {
                return itemImg;
            }
        }
        throw new NoDataFoundException();
    }

    public void delete(Long id) {
        itemImgMapper.delete(id);
    }
}
