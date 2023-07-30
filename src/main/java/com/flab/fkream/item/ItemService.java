package com.flab.fkream.item;

import com.flab.fkream.AutoComplete.AutoCompleteService;
import com.flab.fkream.brand.Brand;
import com.flab.fkream.brand.BrandService;
import com.flab.fkream.error.exception.NoDataFoundException;
import com.flab.fkream.interestItemCount.InterestItemCountService;
import com.flab.fkream.kafka.KafkaMessageSender;
import com.flab.fkream.kafka.KafkaTopic;
import com.flab.fkream.mapper.ItemMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ItemService {

    private final ItemMapper itemMapper;
    private final BrandService brandService;
    private final KafkaMessageSender messageSender;
    private final AutoCompleteService autoCompleteService;
    private final InterestItemCountService interestItemCountService;

    public void addItem(Item itemInfo) {
        itemInfo.setCreatedAtToNow();
        itemMapper.save(itemInfo);
        autoCompleteService.addItem(itemInfo);
        interestItemCountService.save(itemInfo);
        messageSender.send(KafkaTopic.ITEM_TOPIC, itemInfo);
    }

    public Item findOne(Long itemId) {
        Item item = itemMapper.findOne(itemId);
        if (item == null) {
            throw new NoDataFoundException();
        }
        Brand brand = brandService.findOne(item.getBrand().getId());
        item.setBrand(brand);
        return item;
    }


    public List<Item> findAll() {
        List<Item> items = itemMapper.findAll();
        for (Item item : items) {
            item.setBrand(brandService.findOne(item.getBrand().getId()));
        }
        return items;
    }

    public void update(Item itemInfo) {
        itemInfo.setModifiedAtToNow();
        itemMapper.update(itemInfo);
    }

    public void delete(Long id) {
        itemMapper.delete(id);
    }

}
