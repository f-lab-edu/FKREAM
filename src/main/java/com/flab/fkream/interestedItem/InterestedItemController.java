package com.flab.fkream.interestedItem;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InterestedItemController {

    private final InterestedItemService interestedItemService;

    @PostMapping("/interested-items")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody InterestedItem interestedItemInfo) {
        interestedItemService.save(interestedItemInfo);
    }

    @GetMapping("/interested-items/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<InterestedItem> findAllByUserId(@PathVariable Long id) {
        return interestedItemService.findAllByUserId(id);
    }

    @DeleteMapping("/interested-items/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        interestedItemService.delete(id);
    }
}
