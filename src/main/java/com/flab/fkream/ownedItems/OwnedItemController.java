package com.flab.fkream.ownedItems;

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
public class OwnedItemController {
	private final OwnedItemService ownedItemService;

	@PostMapping("/ownedItem")
	public HttpStatus addOwnedItem(@RequestBody OwnedItem ownedItemInfo) {
		ownedItemService.addOwnedItem(ownedItemInfo);
		return HttpStatus.CREATED;
	}

	@GetMapping("/ownedItems")
	public List<OwnedItem> findAll() {
		return ownedItemService.findAll();
	}

	@GetMapping("/ownedItem/{id}")
	public OwnedItem findOne(@PathVariable Long id) {
		return ownedItemService.findOne(id);
	}
}
