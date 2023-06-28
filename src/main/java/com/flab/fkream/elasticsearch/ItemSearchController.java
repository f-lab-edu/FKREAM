package com.flab.fkream.elasticsearch;

import com.flab.fkream.deal.Deal;
import com.flab.fkream.item.Item;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.kafka.KafkaMessageSender;
import com.flab.fkream.kafka.KafkaTopic;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemSearchController {

    private final ItemSearchService itemSearchService;
    private final KafkaMessageSender messageSender;


    @GetMapping("/estest1")
    public void test1() {
        ItemSizePrice itemSizePrice = new ItemSizePrice(1L, 1L, "M", 12000, 6000,
            LocalDateTime.now());
        messageSender.send(KafkaTopic.ITEM_PURCHASE_PRICE, itemSizePrice);
    }


    @GetMapping("/estest2")
    public void test2() {

        Item item1 = Item.builder()
            .id(1L).build();
        ItemSizePrice item1SizePrice = new ItemSizePrice(1L, item1.getId(), "S", 10000, 6000,
            LocalDateTime.now());

        Deal deal = Deal.builder()
            .id(1L).price(20000).item(item1).size(item1SizePrice.getSize()).build();

        messageSender.send(KafkaTopic.DEAL, deal);
    }

    @PostMapping("/item_document")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveItem(@RequestBody ItemDocument itemDocument) {
        itemSearchService.saveItem(itemDocument);
    }

    @PostMapping("/item_documents")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAllItems(@RequestBody List<ItemDocument> itemDocumentList) {
        itemSearchService.saveAllItem(itemDocumentList);
    }

    @PostMapping("/item_search")
    @ResponseStatus(HttpStatus.OK)
    public ItemDocumentsDto findBySearchCriteria(@RequestBody ElasticSearchCriteria searchCriteria)
        throws IOException {

        return itemSearchService.findBySearchCriteria(searchCriteria);
    }
}
