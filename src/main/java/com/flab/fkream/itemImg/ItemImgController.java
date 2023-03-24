package com.flab.fkream.itemImg;

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
public class ItemImgController {
	private final ItemImgService itemImgService;

	@PostMapping("/itemImg")
	public HttpStatus addItemImg(@RequestBody ItemImg itemImgInfo) {
		itemImgService.addItemImg(itemImgInfo);
		return HttpStatus.CREATED;
	}

	@GetMapping("/itemImgs")
	public List<ItemImg> findAll() {
		return itemImgService.findAll();
	}

	@GetMapping("/itemImg/{id}")
	public ItemImg findOne(@PathVariable Long id) {
		return itemImgService.findOne(id);
	}
}
