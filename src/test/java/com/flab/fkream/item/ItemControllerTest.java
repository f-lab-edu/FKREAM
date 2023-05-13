package com.flab.fkream.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.flab.fkream.brand.Brand;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
@ActiveProfiles({"test"})
class ItemControllerTest {

    @MockBean
    ItemService itemService;
    @Autowired
    MockMvc mockMvc;

    Brand brand = Brand.builder().brandName("구찌").isLuxury(true).build();

    Item itemInfo =
        Item.builder()
            .itemName("나이키 에어포스")
            .modelNumber("NK22035")
            .categoryId(1L)
            .detailedCategoryId(2L)
            .releaseDate(LocalDate.now())
            .representativeColor("Black")
            .releasedPrice(10000)
            .brand(brand)
            .build();

    @Test
    void 아이템_추가() throws Exception {

        doNothing().when(itemService).addItem(itemInfo);
        mockMvc
            .perform(
                post("/items").contentType(MediaType.APPLICATION_JSON)
                    .content(getContent(itemInfo)))
            .andExpect(status().isOk());
    }

    @Test
    void 전체_조회() throws Exception {
        given(itemService.findAll()).willReturn(List.of());
        mockMvc
            .perform(get("/items").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void id_조회() throws Exception {
        given(itemService.findOne(1L)).willReturn(itemInfo);
        mockMvc
            .perform(get("/items/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void 업데이트() throws Exception {
        doNothing().when(itemService).update(itemInfo);
        mockMvc
            .perform(
                patch("/items/1").contentType(MediaType.APPLICATION_JSON)
                    .content(getContent(itemInfo)))
            .andExpect(status().isOk());
    }

    @Test
    void 삭제() throws Exception {
        doNothing().when(itemService).delete(1L);
        mockMvc
            .perform(delete("/items/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    private String getContent(Item itemInfo) throws JsonProcessingException {
        return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(itemInfo);
    }
}
