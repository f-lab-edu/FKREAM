package com.flab.fkream.login;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.flab.fkream.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.fkream.user.UserService;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Test
    public void testLoginSuccess() throws Exception {
        LoginForm loginForm = new LoginForm("test", "test");

        User user = User.builder().email("test").password("test").build();

        given(userService.login(loginForm)).willReturn(user);

        mockMvc
            .perform(
                post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(loginForm)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.users.email").value(user.getEmail()));
    }

    @Test
    public void testLoginFail() throws Exception {
        LoginForm loginForm = new LoginForm("test@test.com", "test");

        given(userService.login(loginForm)).willReturn(null);

        mockMvc
            .perform(
                post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(loginForm)))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void testLogout() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("LOGIN_USERS_ID", 1L);

        mockMvc.perform(get("/logout").session(session)).andExpect(status().isOk());

        assertNull(session.getAttribute("LOGIN_USERS_ID"));
    }
}
