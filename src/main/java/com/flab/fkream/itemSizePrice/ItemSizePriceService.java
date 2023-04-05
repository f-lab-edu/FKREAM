package com.flab.fkream.itemSizePrice;

import com.flab.fkream.error.exception.NoDataFoundException;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ItemSizePriceService {

    private final ItemSizePriceMapper itemSizePriceMapper;

    public void addItemSizePrice(ItemSizePrice itemSizePriceInfo) {
        itemSizePriceMapper.save(itemSizePriceInfo);
    }

    public ItemSizePrice findOne(Long id) {
        ItemSizePrice itemSizePrice = itemSizePriceMapper.findOne(id);
        if (itemSizePrice == null) {
            throw new NoDataFoundException();
        }
        return itemSizePrice;
    }

    public List<ItemSizePrice> findAllByItemId(Long itemId) {
        List<ItemSizePrice> itemSizePrices = itemSizePriceMapper.findAllByItemId(itemId);
        if (itemSizePrices.size() == 0) {
            throw new NoDataFoundException();
        }
        return itemSizePrices;
    }

    public ItemSizePrice findByItemIdAndSize(Long itemId, String size) {
        return itemSizePriceMapper.findByItemIdAndSize(itemId, size);

    }

    public void delete(Long id) {
        itemSizePriceMapper.delete(id);
    }
}
