package com.flab.fkream.users;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Transactional
	void signUp() throws Exception {
		//given
		Users user = Users.builder()
			.email("testmail")
			.password("0000")
			.fourteenAgreement(true)
			.adAgreement(true)
			.personalAuthentication(true)
			.gender("male")
			.phoneNumber("01011112222")
			.name("kim")
			.rank("Silver")
			.profileImgName("nickname")
			.profileImgUrl("qweasd@qweasd")
			.profileName("test")
			.profileImgOriginName("originName")
			.build();

		//when

		//then
		mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(user)))
			.andExpect(status().isCreated());

	}

}