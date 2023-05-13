package com.flab.fkream.deal;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.flab.fkream.brand.Brand;
import com.flab.fkream.item.Item;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(DealController.class)
@ActiveProfiles({"test"})
class DealControllerTest {

    @MockBean
    DealService dealService;

    @Autowired
    MockMvc mockMvc;

    Brand brand = Brand.builder().brandName("구찌").isLuxury(true).build();

    Item itemInfo =
        Item.builder()
            .itemName("나이키 에어포스")
            .modelNumber("NK22035")
            .releaseDate(LocalDate.now())
            .representativeColor("Black")
            .releasedPrice(10000)
            .brand(brand)
            .build();

    Deal saleDealInfo = Deal.builder()
        .item(itemInfo)
        .dealType(DealType.SALE)
        .userId(1L)
        .price(20000)
        .size("255")
        .period(LocalDate.now())
        .utilizationPolicy(true)
        .salesCondition(true)
        .status(Status.BIDDING)
        .build();

    Deal purchaseDealInfo = Deal.builder()
        .item(itemInfo)
        .dealType(DealType.PURCHASE)
        .userId(1L)
        .price(20000)
        .size("255")
        .period(LocalDate.now())
        .utilizationPolicy(true)
        .salesCondition(true)
        .status(Status.BIDDING)
        .build();

    MarketPriceDto marketPriceDto = MarketPriceDto.builder().size("260").build();
    BiddingPriceDto biddingPriceDto = BiddingPriceDto.builder().build();

    DealHistoryDto dealHistoryDto = DealHistoryDto.builder().build();

    @Test
    void sales() throws Exception {
        doNothing().when(dealService).sale(saleDealInfo);
        mockMvc.perform(post("/deals/sales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getContent(saleDealInfo)))
            .andExpect(status().isOk());
    }

    @Test
    void purchase() throws Exception {
        doNothing().when(dealService).sale(purchaseDealInfo);
        mockMvc.perform(post("/deals/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getContent(purchaseDealInfo)))
            .andExpect(status().isOk());
    }

    @Test
    void findByUserId() throws Exception {
        given(dealService.findByUserId()).willReturn(List.of(saleDealInfo));
        mockMvc.perform(get("/deals"))
            .andExpect(status().isOk());
    }

    @Test
    void findById() throws Exception {
        given(dealService.findById(1L)).willReturn(saleDealInfo);
        mockMvc.perform(get("/deals/1")).andExpect(status().isOk());
    }

    @Test
    void progressToComplete() throws Exception {
        doNothing().when(dealService).completeDeal(1L);
        mockMvc.perform(post("/deals/complete/1")).andExpect(status().isOk());
    }

    @Test
    void progressToCancel() throws Exception {
        doNothing().when(dealService).cancelDeal(1L);
        mockMvc.perform(post("/deals/cancel/1")).andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        doNothing().when(dealService).delete(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/deals/1")).andExpect(status().isOk());
    }

    @Test
    void findMarketPriceInGraph() throws Exception {
        given(dealService.findMarketPriceInGraph(1L, DealPeriod.ONE_YEAR, "260")).willReturn(
            List.of(marketPriceDto));
        given(dealService.findMarketPriceInGraph(1L, null, null)).willReturn(
            List.of(marketPriceDto));

        mockMvc.perform(get("/deals/market-prices-in-graph")
            .param("itemId", "1")
            .param("period", "ONE_YEAR")
            .param("size", "260")).andExpect(status().isOk());
        mockMvc.perform(get("/deals/market-prices-in-graph")
            .param("itemId", "1")).andExpect(status().isOk());
    }

    @Test
    void findMarketPrices() throws Exception {
        given(dealService.findMarketPrices(1L, "260")).willReturn(List.of(marketPriceDto));
        mockMvc.perform(get("/deals/market-prices")
                .param("itemId", "1")
                .param("size", "255"))
            .andExpect(status().isOk());
    }

    @Test
    void findBiddingPrices() throws Exception {
        given(dealService.findBiddingPrices(1L, "255", DealType.PURCHASE))
            .willReturn(List.of(biddingPriceDto));
        mockMvc.perform(get("/deals/bidding-prices")
                .param("itemId", "1")
                .param("size", "255")
                .param("dealType", "PURCHASE"))
            .andExpect(status().isOk());
    }

    @Test
    void findHistoryCount() throws Exception {
        DealHistoryCountDto dealHistoryCountDto = DealHistoryCountDto.builder().build();
        given(dealService.findHistoryCount(DealType.PURCHASE)).willReturn(
            Map.of());
        mockMvc.perform(get("/deals/history-counts")
            .param("dealType", "PURCHASE")).andExpect(status().isOk());
    }

    @Test
    void findPurchaseHistory() throws Exception {
        given(dealService.findPurchaseHistories(Status.BIDDING)).willReturn(
            List.of(dealHistoryDto));
        mockMvc.perform(get("/deals/purchase-histories").param("status", "BIDDING"))
            .andExpect(status().isOk());
    }

    @Test
    void findSaleHistory() throws Exception {
        given(dealService.findSaleHistories(Status.BIDDING)).willReturn(List.of(dealHistoryDto));
        mockMvc.perform(get("/deals/sale-histories").param("status", "BIDDING"))
            .andExpect(status().isOk());
    }


    private String getContent(Deal deal) throws JsonProcessingException {
        return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(deal);
    }
}