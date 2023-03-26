package com.flab.fkream.itemSizePrice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ItemSizePriceController {
	private final ItemSizePriceService itemSizePriceService;

	@PostMapping("/itemSizePrice")
	public HttpStatus addItemSizePrice(@RequestBody ItemSizePrice itemSizePriceInfo) {
		itemSizePriceService.addItemSizePrice(itemSizePriceInfo);
		return HttpStatus.CREATED;
	}

	@GetMapping("/itemSizePrices")
	public List<ItemSizePrice> findAll() {
		return itemSizePriceService.findAll();
	}

	@GetMapping("/itemSizePrice/{id}")
	public ItemSizePrice findOne(@PathVariable Long id) {
		return itemSizePriceService.findOne(id);
	}
}
