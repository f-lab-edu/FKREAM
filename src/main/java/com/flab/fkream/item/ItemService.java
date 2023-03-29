package com.flab.fkream.item;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ItemService {
    private final ItemMapper itemMapper;

    /**
     *
     */
    public void addItem(Item itemInfo) {
        itemInfo.setCreatedAt();
        int itemId = itemMapper.save(itemInfo);
        if (itemId != 1) {
            log.error("insert item error! itemInfo : {}", itemInfo);
            throw new NullPointerException("insert item error!" + itemInfo);
        }
    }

    public Item findOne(Long itemId) {
        Item item = itemMapper.findOne(itemId);
        if (item == null) {
            log.error("find item error! itemId : {}", itemId);
            throw new NullPointerException("find item error! itemId : " + itemId);
        }
        return item;
    }

    public List<Item> findAll() {
        List<Item> items = itemMapper.findAll();
        if(items == null){
            log.error("find all error!");
            throw new NullPointerException("find all error!");
        }
        return items;
    }


    public void update( Item itemInfo) {
        itemInfo.setModifiedAt();
        int result = itemMapper.update(itemInfo);
        if (result != 1) {
            log.error("update item error! {}", itemInfo);
            throw new RuntimeException("update item error");
        }
    }

    public void delete(Long id) {
        int result = itemMapper.delete(id);
        if (result != 1) {
            log.error("delete item error!");
            throw new RuntimeException("delete item error");
        }
    }
}
