package com.flab.fkream.itemSizePrice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.fkream.brand.Brand;
import com.flab.fkream.item.Item;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.stubbing.Stubber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ItemSizePriceController.class)
class ItemSizePriceControllerTest {

    @MockBean
    ItemSizePriceService itemSizePriceService;

    @Autowired
    MockMvc mockMvc;

    private static final Long ITEM_ID = 1L;
    private static final String SIZE = "260";

    ItemSizePrice itemSizePriceInfo = ItemSizePrice.builder()
        .itemId(ITEM_ID)
        .size(SIZE)
        .build();

    @Test
    void addItemSizePrice() throws Exception {
        doNothing().when(itemSizePriceService).addItemSizePrice(itemSizePriceInfo);
        mockMvc
            .perform(
                post("/itemSizePrices").contentType(MediaType.APPLICATION_JSON)
                    .content(getContent(itemSizePriceInfo)))
            .andExpect(status().isOk());
    }

    @Test
    void findAllByItemId() throws Exception {
        given(itemSizePriceService.findAllByItemId(ITEM_ID)).willReturn(List.of());
        mockMvc
            .perform(get("/itemSizePrices/byItem/1"))
            .andExpect(status().isOk());
    }

    @Test
    void findByItemIdAndSize() throws Exception {
        given(itemSizePriceService.findByItemIdAndSize(ITEM_ID, SIZE)).willReturn(
            itemSizePriceInfo);
        mockMvc.perform(get("/itemSizePrices/1/260")).andExpect(status().isOk());
    }

    @Test
    void findOne() throws Exception {
        given(itemSizePriceService.findOne(1L)).willReturn(itemSizePriceInfo);
        mockMvc.perform(get("/itemSizePrices/1")).andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        doNothing().when(itemSizePriceService).delete(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/itemSizePrices/1")).andExpect(status().isOk());
    }

    private String getContent(ItemSizePrice itemSizePriceInfo) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(itemSizePriceInfo);
    }
}