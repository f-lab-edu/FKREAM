package com.flab.fkream.deal;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deals")
public class DealController {

    private final DealService dealService;

    @PostMapping("/sales")
    public void sales(@Valid @RequestBody Deal deal) {
        dealService.sale(deal);
    }

    @PostMapping("/purchases")
    public void purchase(@Valid @RequestBody Deal deal) {
        dealService.purchase(deal);
    }

    @GetMapping()
    public List<Deal> findByUserId() {
        return dealService.findByUserId();
    }

    @GetMapping("/{id}")
    public Deal findById(@PathVariable Long id) {
        return dealService.findById(id);
    }

    @PostMapping("/complete/{id}")
    public void progressToComplete(@PathVariable Long id) {
        dealService.completeDeal(id);
    }

    @PostMapping("/cancel/{id}")
    public void progressToCancel(@PathVariable Long id) {
        dealService.cancelDeal(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        dealService.delete(id);
    }

    @GetMapping("/market-prices-in-graph")
    public List<MarketPriceDto> findMarketPriceInGraph(@RequestParam Long itemId,
        @RequestParam(required = false) DealPeriod period,
        @RequestParam(required = false) String size) {
        return dealService.findMarketPriceInGraph(itemId, period, size);
    }

    @GetMapping("/market-prices")
    public List<MarketPriceDto> findMarketPrices(@RequestParam Long itemId,
        @RequestParam(required = false) String size) {
        return dealService.findMarketPrices(itemId, size);
    }

    @GetMapping("/bidding-prices")
    public List<BiddingPriceDto> findBiddingPrices(@RequestParam Long itemId,
        @RequestParam(required = false) String size,
        @RequestParam DealType dealType) {
        return dealService.findBiddingPrices(itemId, size, dealType);
    }

    @GetMapping("/history-counts")
    public Map<Status, Integer> findHistoryCount(@RequestParam DealType dealType) {
        return dealService.findHistoryCount(dealType);
    }

    @GetMapping("/purchase-histories")
    public List<DealHistoryDto> findPurchaseHistories(@RequestParam Status status) {
        return dealService.findPurchaseHistories(status);
    }

    @GetMapping("/sale-histories")
    public List<DealHistoryDto> findSaleHistories(@RequestParam Status status) {
        return dealService.findSaleHistories(status);
    }


}
