package com.flab.fkream.itemCategory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.fkream.address.Address;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ItemCategoryController.class)
class ItemCategoryControllerTest {

    @MockBean
    ItemCategoryService itemCategoryService;

    @Autowired
    MockMvc mockMvc;

    ItemCategory itemCategory = ItemCategory.builder().name("신발")
        .build();

    @Test
    void addCategory() throws Exception {
        doNothing().when(itemCategoryService).addCategory(itemCategory);
        mockMvc.perform(post("/categories").contentType(MediaType.APPLICATION_JSON).content(getContent(itemCategory))).andExpect(status().isOk());
    }

    @Test
    void findAll() throws Exception {
        given(itemCategoryService.findAllCategory()).willReturn(List.of(new ItemCategoryDto(itemCategory,List.of())));
        mockMvc.perform(get("/categories")).andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        doNothing().when(itemCategoryService).delete(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/1")).andExpect(status().isOk());
    }

    private String getContent(ItemCategory itemCategory) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(itemCategory);
    }
}