package com.flab.fkream.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.fkream.AutoComplete.AutoCompletedItemDto;
import com.flab.fkream.AutoComplete.Trie;
import com.flab.fkream.brand.Brand;
import com.flab.fkream.brand.BrandService;
import com.flab.fkream.error.exception.NoDataFoundException;
import com.flab.fkream.interestItemCount.InterestItemCountService;
import com.flab.fkream.itemImg.ItemImg;
import com.flab.fkream.itemImg.ItemImgService;
import com.flab.fkream.kafka.KafkaMessageSender;
import com.flab.fkream.kafka.KafkaTopic;
import com.flab.fkream.mapper.ItemMapper;
import com.flab.fkream.search.elasticsearch.ItemSearchRepository;
import io.lettuce.core.RedisException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ItemService {

    private static final String REDIS_AUTO_COMPLETE_KEY = "AutoComplete";

    private final ItemMapper itemMapper;
    private final BrandService brandService;
    private final KafkaMessageSender messageSender;
    private final InterestItemCountService interestItemCountService;
    private final ItemImgService itemImgService;
    private final ItemSearchRepository itemSearchRepository;

    private final RedisTemplate<String, String> redisCacheTemplate;
    private final RedisTemplate<String, String> redisShardingTemplate;

    public void addItem(Item itemInfo) {
        itemInfo.setCreatedAtToNow();
        itemMapper.save(itemInfo);
        addAutoCompleteItem(itemInfo);
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

    public List<Item> findByPrefix(String content) {
        return itemMapper.findByPrefix(content);
    }


    public List<Item> findAll() {
        List<Item> items = itemMapper.findAll();
        /*for (Item item : items) {
            item.setBrand(brandService.findOne(item.getBrand().getId()));
        }*/
        return items;
    }

    public void update(Item itemInfo) {
        itemInfo.setModifiedAtToNow();
        itemMapper.update(itemInfo);
    }

    public void delete(Long id) {
        itemMapper.delete(id);
    }


    public List<AutoCompletedItemDto> autoComplete(String word) throws JsonProcessingException {
        if (word.isBlank()) {
            return List.of();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<AutoCompletedItemDto> results = new ArrayList<>();

        List<RedisTemplate> redisTemplates = new ArrayList<>();

        // db a b c d e/ f g h i j/ k l m n o/ p q r s t /u v w x y/ z

        try {
            List<String> i = redisCacheTemplate.opsForList().range("i", 0, 5);
            for (String s : i) {
                AutoCompletedItemDto item = objectMapper.readValue(s, AutoCompletedItemDto.class);
                results.add(item);
            }
        } catch (RedisException e) {
            List<Item> items = autoCompleteByES(word);
            results = AutoCompletedItemDto.of(items);
        }
        return results;
    }

    public List<Item> autoCompleteByDB(String word) {
        if (word.isBlank()) {
            return List.of();
        }
        List<Item> results = findByPrefix(word);
        return results;
    }


    public void addAutoCompleteItem(Item item) {
        /*Trie trie = (Trie)valueOps.get(REDIS_AUTO_COMPLETE_KEY);
        if (trie == null) {
            throw new NoDataFoundException();
        }
        trie.insert(item);
        valueOps.set(REDIS_AUTO_COMPLETE_KEY, trie);*/
    }


    public void initTrie() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        List<Item> all = findAll();
        Trie trie = new Trie();

        int count = 0;
        for (Item item : all) {
            trie.insert(item);
            count++;
            log.info("count: " + count);
        }
        List<Item> items = trie.search("i");
        log.info(items.size());
        List<AutoCompletedItemDto> autoCompletedItemDtos = new ArrayList<>();
        for (Item item : items) {
            autoCompletedItemDtos.add(AutoCompletedItemDto.of(item));
        }

        for (int i = 0; i < autoCompletedItemDtos.size(); i++) {
            autoCompletedItemDtos.get(i).setCount(i);
        }

        PriorityQueue<AutoCompletedItemDto> priorityQueue = new PriorityQueue(
            Collections.reverseOrder());
        for (AutoCompletedItemDto autoCompletedItemDto : autoCompletedItemDtos) {
            priorityQueue.add(autoCompletedItemDto);
        }

        ListOperations<String, String> listOperations = redisCacheTemplate.opsForList();
        redisCacheTemplate.delete("i");

        for (int i = 0; i < 5; i++) {
            AutoCompletedItemDto autoCompletedItemDto = priorityQueue.poll();
            String s = objectMapper.writeValueAsString(autoCompletedItemDto);
            listOperations.rightPush("i", s);
        }
    }

    public List<Item> autoCompleteByES(String word) {
        if (word.isBlank()) {
            return List.of();
        }
        itemSearchRepository.findTop5ByItemNameStartingWithOrderByDealCount(word);
        List<Item> results = findByPrefix(word);
        return results;
    }

}
