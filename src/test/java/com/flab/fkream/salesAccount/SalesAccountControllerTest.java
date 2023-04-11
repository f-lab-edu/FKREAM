package com.flab.fkream.salesAccount;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.flab.fkream.user.User;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(SalesAccountController.class)
class SalesAccountControllerTest {

    @MockBean
    SalesAccountService salesAccountService;
    @Autowired
    MockMvc mockMvc;

    SalesAccount salesAccountInfo = SalesAccount.builder()
        .id(1L)
        .userId(1L)
        .bankName("Test Bank")
        .accountNumber("123-456-7890")
        .accountHolder("Test User")
        .build();

    @Test
    void save() throws Exception {
        // Given
        doNothing().when(salesAccountService).save(salesAccountInfo);

        // When & Then
        mockMvc.perform(post("/sales-accounts")
                .content(new ObjectMapper().writeValueAsString(salesAccountInfo))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    void findAll() throws Exception {
        // Given
        given(salesAccountService.findAll()).willReturn(
            Collections.singletonList(salesAccountInfo));

        // When & Then
        mockMvc.perform(get("/sales-accounts"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].bankName").value(salesAccountInfo.getBankName()))
            .andExpect(jsonPath("$[0].accountNumber").value(salesAccountInfo.getAccountNumber()))
            .andExpect(jsonPath("$[0].accountHolder").value(salesAccountInfo.getAccountHolder()));

    }

    @Test
    void findOne() throws Exception {
        // Given
        long id = salesAccountInfo.getId();
        given(salesAccountService.findById(id)).willReturn(salesAccountInfo);

        // When & Then
        mockMvc.perform(get("/sales-accounts/{id}", id))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.userId").value(salesAccountInfo.getUserId()))
            .andExpect(jsonPath("$.bankName").value(salesAccountInfo.getBankName()))
            .andExpect(jsonPath("$.accountNumber").value(salesAccountInfo.getAccountNumber()))
            .andExpect(jsonPath("$.accountHolder").value(salesAccountInfo.getAccountHolder()));
    }

    @Test
    void update() throws Exception {
        // Given
        long id = salesAccountInfo.getId();
        SalesAccount salesAccountToUpdate = SalesAccount.builder()
            .id(id)
            .bankName("New Test Bank")
            .accountNumber("123-456-7890")
            .accountHolder("New Test User")
            .build();
        given(salesAccountService.findById(id)).willReturn(salesAccountInfo);

        // When
        mockMvc.perform(patch("/sales-accounts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(salesAccountToUpdate)))
            .andExpect(status().isOk());

        // Then
        then(salesAccountService).should().update(ArgumentMatchers.any(SalesAccount.class));
    }

    @Test
    void delete() throws Exception {
        // Given
        long id = salesAccountInfo.getId();
        doNothing().when(salesAccountService).deleteById(id);

        // When
        salesAccountService.deleteById(id);

        // Then
        then(salesAccountService).should().deleteById(id);
    }
}