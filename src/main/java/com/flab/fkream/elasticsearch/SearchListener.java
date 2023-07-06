package com.flab.fkream.elasticsearch;

import com.flab.fkream.deal.Deal;
import com.flab.fkream.item.Item;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.itemSizePrice.ItemSizePriceService;
import com.flab.fkream.kafka.KafkaTopic;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class SearchListener {

    private final ItemSearchRepository itemSearchRepository;
    private final ItemSizePriceService itemSizePriceService;

    @KafkaListener(topics = KafkaTopic.ITEM_TOPIC, groupId = "${spring.kafka.group-id}")
    public void listenItem(ConsumerRecord<String, Object> record) {
        Item item = (Item) record.value();

        List<ItemSizePrice> itemSizes = itemSizePriceService.findAllByItemId(item.getId());
        List<String> sizes = new ArrayList<>();
        for (ItemSizePrice itemSize : itemSizes) {
            sizes.add(itemSize.getSize());
        }
        ItemDocument itemDocument = ItemDocument.of(item, sizes);
        itemSearchRepository.save(itemDocument);
    }

    @KafkaListener(topics = KafkaTopic.DEAL, groupId = "${spring.kafka.group-id}")
    public void listenDeal(ConsumerRecord<String, Object> record) {
        Deal deal = (Deal) record.value();
        Long itemId = deal.getItem().getId();
        ItemDocument itemDocument = itemSearchRepository.findById(itemId).orElseThrow();

        // deal count 증가, 사이즈별 프리미엄율 변경
        itemDocument.increaseCount();
        itemDocument.updatePremiumRateBySize(deal.getSize(), deal.getPrice());
        itemDocument.updateMinPremiumRate();
        itemSearchRepository.save(itemDocument);
    }

    @KafkaListener(topics = KafkaTopic.ITEM_PURCHASE_PRICE, groupId = "${spring.kafka.group-id}")
    public void listenItemPurchasePrice(ConsumerRecord<String, Object> record) {
        ItemSizePrice value = (ItemSizePrice) record.value();

        ItemDocument itemDocument = itemSearchRepository.findById(value.getItemId()).orElseThrow();
        Map<String, Integer> immediatePurchasePriceBySize = itemDocument.getImmediatePurchasePriceBySize();
        int nowPrice = immediatePurchasePriceBySize.get(value.getSize());

        if (nowPrice > value.getImmediatePurchasePrice()) {
            immediatePurchasePriceBySize.put(value.getSize(), value.getImmediatePurchasePrice());
        }

        itemSearchRepository.save(itemDocument);
    }
}
