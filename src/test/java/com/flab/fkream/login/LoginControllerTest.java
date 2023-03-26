package com.flab.fkream.login;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.fkream.users.Users;
import com.flab.fkream.users.UsersService;

@WebMvcTest(LoginController.class)
class LoginControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UsersService usersService;

	enum LoginStatus {
		SUCCESS, FAIL, DELETED
	}

	@Test
	public void testLoginSuccess() throws Exception {
		LoginForm loginForm = new LoginForm("test", "test");

		Users user = Users.builder()
			.email("test")
			.password("test")
			.build();

		// given(usersService.login(loginForm)).willReturn(user);

		mockMvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(loginForm)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result").value(LoginStatus.SUCCESS.name()))
			.andExpect(jsonPath("$.users.email").value(user.getEmail()));
	}

	@Test
	public void testLoginFail() throws Exception {
		LoginForm loginForm = new LoginForm("test@test.com", "test");

		given(usersService.login(loginForm)).willReturn(null);

		mockMvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(loginForm)))
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("$.result").value(LoginStatus.FAIL.name()));
	}

	@Test
	public void testLogout() throws Exception {
		// 세션 생성
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("LOGIN_USERS_ID", 1L);

		mockMvc.perform(get("/logout").session(session))
			.andExpect(status().isOk());

		// 세션 삭제 여부 확인
		assertNull(session.getAttribute("LOGIN_USERS_ID"));
	}

}