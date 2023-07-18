package com.flab.fkream.item;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    // item 추가
    @PostMapping("")
    public HttpStatus addItem(@RequestBody @NonNull Item itemInfo) {
        itemService.addItem(itemInfo);
        return HttpStatus.CREATED;
    }

    // item 리스트 조회
    @GetMapping("")
    public List<Item> findAll() {
        return itemService.findAll();
    }

    // item 단건 조회
    @GetMapping("/{id}")
    public Item findOne(@PathVariable Long id) {
        return itemService.findOne(id);
    }

    // item 수정
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody Item itemInfo) {
        itemService.update(itemInfo);
    }

    // item 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }
}
