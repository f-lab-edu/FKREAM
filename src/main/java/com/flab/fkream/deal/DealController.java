package com.flab.fkream.deal;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deals")
public class DealController {

    private final DealService dealService;

    @PostMapping("/sales")
    public void sales(@Valid @RequestBody Deal deal) {
        dealService.saveSale(deal);
    }

    @PostMapping("/purchases")
    public void purchase(@Valid @RequestBody Deal deal) {
        dealService.savePurchase(deal);
    }

    @GetMapping("/user/{userId}")
    public List<Deal> findByUserId(@PathVariable Long userId) {
        return dealService.findByUserId(userId);
    }

    @GetMapping("/{id}")
    public Deal findById(@PathVariable Long id) {
        return dealService.findById(id);
    }

    @PostMapping("/completes/{id}")
    public void progressToComplete(@PathVariable Long id){
        dealService.completeDeal(id);
    }

    @PostMapping("/cancel/{id}")
    public void progressToCancel(@PathVariable Long id){
        dealService.cancelDeal(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        dealService.delete(id);
    }

}
