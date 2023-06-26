package com.flab.fkream.elasticsearch;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemSearchController {

    private final ItemSearchService itemSearchService;

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
