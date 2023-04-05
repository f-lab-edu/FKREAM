package com.flab.fkream.itemSizePrice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/itemSizePrices")
public class ItemSizePriceController {
	private final ItemSizePriceService itemSizePriceService;

	@PostMapping("")
	public HttpStatus addItemSizePrice(@RequestBody ItemSizePrice itemSizePriceInfo) {
		itemSizePriceService.addItemSizePrice(itemSizePriceInfo);
		return HttpStatus.CREATED;
	}

	@GetMapping("/byItem/{itemId}")
	public List<ItemSizePrice> findAllByItemId(@PathVariable Long itemId) {
		return itemSizePriceService.findAllByItemId(itemId);
	}

	@GetMapping("/{itemId}/{size}")
	public ItemSizePrice findByItemIdAndSize(@PathVariable Long itemId, @PathVariable String size) {
		return itemSizePriceService.findByItemIdAndSize(itemId, size);
	}

	@GetMapping("/{id}")
	public ItemSizePrice findOne(@PathVariable Long id) {
		return itemSizePriceService.findOne(id);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id){
		itemSizePriceService.delete(id);
	}
}
