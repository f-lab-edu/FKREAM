package com.flab.fkream.itemImg;

import java.util.List;

import com.flab.fkream.brand.Brand;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ItemImgController {
	private final ItemImgService itemImgService;

	@PostMapping("/itemImgs")
	public HttpStatus addItemImg(@RequestBody ItemImg itemImgInfo) {
		itemImgService.addItemImg(itemImgInfo);
		return HttpStatus.CREATED;
	}

	@GetMapping("/itemImg/{itemId}")
	public List<ItemImg> findImagesByItemId(@PathVariable Long itemId) {
		return itemImgService.findImagesByItemId(itemId);
	}

	@DeleteMapping("/brands/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable Long id){
		itemImgService.delete(id);
}
}
