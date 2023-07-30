package com.flab.fkream.paymentCard;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(PaymentCardController.class)
@ActiveProfiles({"test"})
class PaymentCardControllerTest {

    @MockBean
    PaymentCardService paymentCardService;

    @Autowired
    MockMvc mockMvc;

    PaymentCard paymentCardInfo = PaymentCard.builder()
        .userId(1L)
        .cardCompany("woori")
        .cardNumber("1111222233334444")
        .expiration("12/32")
        .cardPw("1234")
        .build();

    @Test
    void save() throws Exception {
        doNothing().when(paymentCardService).save(paymentCardInfo);
        mockMvc.perform(post("/payment-cards").contentType(MediaType.APPLICATION_JSON)
                .content(getContent(paymentCardInfo)))
            .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void findByUserId() throws Exception {
        given(paymentCardService.findByUserId()).willReturn(List.of(paymentCardInfo));
        mockMvc.perform(get("/payment-cards")).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].cardCompany").value(paymentCardInfo.getCardCompany()));
    }

    @Test
    void findOne() throws Exception {
        given(paymentCardService.findById(1L)).willReturn(paymentCardInfo);
        mockMvc.perform(get("/payment-cards/1")).andExpect(status().isOk())
            .andExpect(jsonPath("cardCompany").value(paymentCardInfo.getCardCompany()));
    }

    @Test
    void delete() throws Exception {
        doNothing().when(paymentCardService).deleteById(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/payment-cards/1"))
            .andExpect(status().isOk());
    }

    private String getContent(PaymentCard paymentCard) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(paymentCard);
    }
}