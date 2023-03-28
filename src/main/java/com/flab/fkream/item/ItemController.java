package com.flab.fkream.item;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ItemController {
	private final ItemService itemService;

	// item 추가
	@PostMapping("/items")
	public HttpStatus addItem(@RequestBody Item itemInfo) {

		itemService.addItem(itemInfo);
		return HttpStatus.CREATED;
	}


	// item 리스트 조회,
	@GetMapping("/items")
	public List<Item> findAll() {
		return itemService.findAll();
	}


	// item 단건 조회
	@GetMapping("/items/{id}")
	public Item findOne(@PathVariable Long id) {
		return itemService.findOne(id);
	}


	// item 수정 Todo: 서비스 수정
	@PatchMapping("/items/{id}")
	public void update(@PathVariable Long id) {

	}

	// item 삭제
	@DeleteMapping ("/items/{id}")
	public void delete(@PathVariable Long id){

	}

}
