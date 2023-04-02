package com.flab.fkream.item;

import com.flab.fkream.brand.Brand;
import com.flab.fkream.brand.BrandService;
import com.flab.fkream.error.exception.NoDataFoundException;
import java.util.List;

import com.flab.fkream.error.exception.MapperException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ItemService {

    private final ItemMapper itemMapper;
    private final BrandService brandService;

    public void addItem(Item itemInfo) {
        try {
            itemInfo.setCreatedAt();
            itemMapper.save(itemInfo);
        } catch (DataAccessException e) {
            log.error("[ItemService.addItem] insert item error! itemInfo : {}", itemInfo);
            throw new MapperException(e);
        }
    }

    public Item findOne(Long itemId) {
        try {
            Item item = itemMapper.findOne(itemId);
            Brand brand = brandService.findOne(item.getBrand().getId());
            item.setBrand(brand);
            if (item == null) {
                throw new NoDataFoundException();
            }
            return item;
        } catch (DataAccessException e) {
            log.error("[ItemService.findOne] find item by id error!");
            throw new MapperException(e);
        }
    }


    public List<Item> findAll() {
        try {
            List<Item> items = itemMapper.findAll();
            if (items == null) {
                throw new NoDataFoundException();
            }
            return items;
        } catch (DataAccessException e) {
            log.error("[ItemService.findAll] find all item error!");
            throw new MapperException(e);
        }
    }

    public void update(Item itemInfo) {
        try {
            itemInfo.setModifiedAt();
            itemMapper.update(itemInfo);
        } catch (DataAccessException e) {
            log.error("[ItemService.update] update item by id error! itemInfo : {}", itemInfo);
            throw new MapperException(e);
        }
    }

    public void delete(Long id) {
        try {
            itemMapper.delete(id);
        } catch (DataAccessException e) {
            log.error("[ItemService.delete] delete item by id error!");
            throw new MapperException(e);
        }
    }
}
