package com.flab.fkream.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.flab.fkream.itemImg.ItemImg;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    User testUser = User.builder()
        .email("test")
        .password("password")
        .fourteenAgreement(true)
        .adAgreement(true)
        .personalAuthentication(true)
        .gender("male")
        .phoneNumber("010-4221-3344")
        .name("john")
        .build();

    User testUser2 = User.builder()
        .email("test")
        .password("password")
        .fourteenAgreement(true)
        .adAgreement(true)
        .personalAuthentication(true)
        .build();

    @Test
    void 회원가입() throws Exception {
        doNothing().when(userService).addUser(any());
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getContent(testUser)))
            .andExpect(status().isCreated());
    }

    @Test
    void 필수값_들어가있지_않은_회원가입() throws Exception{
        doNothing().when(userService).addUser(testUser2);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getContent(testUser2)))
            .andExpect(status().isBadRequest());
    }


    private String getContent(User user) throws JsonProcessingException {
        return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(user);
    }
}