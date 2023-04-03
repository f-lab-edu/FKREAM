package com.flab.fkream.itemSizePrice;

import com.flab.fkream.error.exception.MapperException;
import com.flab.fkream.error.exception.NoDataFoundException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ItemSizePriceService {

    private final ItemSizePriceMapper itemSizePriceMapper;

    public void addItemSizePrice(ItemSizePrice itemSizePriceInfo) {
        try {
            itemSizePriceMapper.save(itemSizePriceInfo);
        } catch (DataAccessException e) {
            log.error(
                "[ItemSizePriceService.addItemSizePrice] insert itemSizePrice error! itemSizePrice : {}",
                itemSizePriceInfo);
            throw new MapperException(e);
        }
    }

    public ItemSizePrice findOne(Long id) {
        try {
            ItemSizePrice itemSizePrice = itemSizePriceMapper.findOne(id);
            if(itemSizePrice == null){
                throw new NoDataFoundException();
            }
            return itemSizePrice;
        } catch (DataAccessException e) {
            log.error(
                "[ItemSizePriceService.findOne] find itemSizePrice error!");
            throw new MapperException(e);
        }
    }

    public List<ItemSizePrice> findAllByItemId(Long itemId) {
        try {
            List<ItemSizePrice> itemSizePrices = itemSizePriceMapper.findAllByItemId(itemId);
            if(itemSizePrices.size()==0){
                throw new NoDataFoundException();
            }
            return itemSizePrices;
        } catch (DataAccessException e) {
            log.error("[ItemSizePriceService.findAllByItemId] find itemSizePrice by itemId error!");
            throw new MapperException(e);
        }
    }

    public ItemSizePrice findByItemIdAndSize(Long itemId, String size) {
        try {
            return itemSizePriceMapper.findByItemIdAndSize(itemId, size);
        } catch (DataAccessException e) {
            log.error(
                "[ItemSizePriceService.findByItemIdAndSize] find itemSizePrice by itemId and size error! size: {}",
                size);
            throw new MapperException(e);
        }
    }

    public void delete(Long id) {
        try {
            itemSizePriceMapper.delete(id);
        } catch (DataAccessException e) {
            log.error("[ItemSizePriceService.delete] delete itemSizePrice error!");
            throw new MapperException(e);
        }
    }
}
