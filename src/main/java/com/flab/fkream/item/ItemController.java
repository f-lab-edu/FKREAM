package com.flab.fkream.item;

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
public class ItemController {
	private final ItemService itemService;

	@PostMapping("/item")
	public HttpStatus addItem(@RequestBody Item itemInfo) {
		itemService.addItem(itemInfo);
		return HttpStatus.CREATED;
	}

	@GetMapping("/items")
	public List<Item> findAll() {
		return itemService.findAll();
	}

	@GetMapping("/item/{id}")
	public Item findOne(@PathVariable Long id) {
		return itemService.findOne(id);
	}
}
