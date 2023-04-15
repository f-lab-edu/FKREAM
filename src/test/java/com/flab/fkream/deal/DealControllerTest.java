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
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(DealController.class)
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
            .category1("신발")
            .category2("스니커즈")
            .releaseDate(LocalDateTime.now())
            .representativeColor("Black")
            .releasedPrice(10000)
            .brand(brand)
            .build();

    Deal saleDealInfo = Deal.builder()
        .item(itemInfo)
        .kindOfDeal(KindOfDeal.SALE)
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
        .kindOfDeal(KindOfDeal.PURCHASE)
        .userId(1L)
        .price(20000)
        .size("255")
        .period(LocalDate.now())
        .utilizationPolicy(true)
        .salesCondition(true)
        .status(Status.BIDDING)
        .build();

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

    private String getContent(Deal deal) throws JsonProcessingException {
        return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(deal);
    }
}