package com.flab.fkream.address;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(AddressController.class)
class AddressControllerTest {

    @MockBean
    AddressService addressService;

    @Autowired
    MockMvc mockMvc;

    Address addressInfo = Address.builder()
        .id(1L)
        .userId(2L)
        .name("john")
        .phoneNumber("01012341234")
        .zipcode("20555")
        .detail1("test address")
        .detail2("123-3")
        .build();

    @Test
    void save() throws Exception {
        doNothing().when(addressService).addAddress(addressInfo);
        mockMvc.perform(post("/address").contentType(MediaType.APPLICATION_JSON)
            .content(getContent(addressInfo))).andExpect(
            status().isOk());
    }

    @Test
    void findOne() throws Exception {
        given(addressService.findOne(1L)).willReturn(addressInfo);
        mockMvc.perform(get("/address/1"))
            .andExpect(status().isOk())
            .andExpect(content().string(getContent(addressInfo)));
    }

    @Test
    void findByUserId() throws Exception {
        given(addressService.findByUserId(1l)).willReturn(List.of(addressInfo));
        mockMvc.perform(get("/address/user/1")).andExpect(status().isOk());
    }

    @Test
    void update() throws Exception {
        doNothing().when(addressService).update(addressInfo);
        mockMvc.perform(patch("/address/1").contentType(MediaType.APPLICATION_JSON)
            .content(getContent(addressInfo))).andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        doNothing().when(addressService).delete(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/address/1")).andExpect(status().isOk());
    }

    private String getContent(Address address) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(address);
    }
}