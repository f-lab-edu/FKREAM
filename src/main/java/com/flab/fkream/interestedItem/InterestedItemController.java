package com.flab.fkream.interestedItem;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class InterestedItemController {
	private final InterestedItemService interestedItemService;

	@PostMapping("/interestedItem")
	public HttpStatus addInterestedItem(@RequestBody InterestedItem interestedItemInfo) {
		interestedItemService.addInterestedItem(interestedItemInfo);
		return HttpStatus.CREATED;
	}

	@GetMapping("/interestedItems")
	public List<InterestedItem> findALl() {
		return interestedItemService.findAll();
	}

	@GetMapping("/interestedItem/{id}")
	public InterestedItem findOne(@PathVariable Long id) {
		return interestedItemService.findOne(id);
	}
}
