package com.flab.fkream.brand;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.flab.fkream.item.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BrandController.class)
class BrandControllerTest {

    @MockBean
    BrandService brandService;

    @Autowired
    MockMvc mockMvc;

    Brand brandInfo = Brand.builder()
            .brandName("샤넬")
            .isLuxury(true).build();

    @Test
    void addBrand() throws Exception {
        doNothing().when(brandService).addBrand(brandInfo);
        mockMvc.perform(post("/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getContent(brandInfo)))
                .andExpect(status().isOk());
    }

    @Test
    void findAll() throws Exception {
        given(brandService.findAll()).willReturn(List.of());
        mockMvc.perform(get("/brands")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findOne() throws Exception {
        given(brandService.findOne(1L)).willReturn(brandInfo);
        mockMvc.perform(get("/brands/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void update() throws Exception {
        doNothing().when(brandService).update(brandInfo);
        mockMvc.perform(patch("/brands/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getContent(brandInfo)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBrand() throws Exception {
        doNothing().when(brandService).delete(1L);
        mockMvc.perform(delete("/brands/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private String getContent(Brand brandInfo) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(brandInfo);
    }
}