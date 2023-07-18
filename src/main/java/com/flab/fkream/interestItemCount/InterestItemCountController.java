package com.flab.fkream.interestItemCount;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interestItemCounts")
public class InterestItemCountController {

    private final InterestItemCountService interestItemCountService;

    @GetMapping("/{itemId}")
    public InterestItemCount findByItemId(@PathVariable Long itemId) {
        return interestItemCountService.findByItemId(itemId);
    }

    @PostMapping("/increase")
    public void increaseCount(@RequestBody Long itemId) {
        interestItemCountService.increaseCount(itemId);
    }

    @PostMapping("/decrease")
    public void decreaseCount(@RequestBody Long itemId) {
        interestItemCountService.decreaseCount(itemId);
    }
}
