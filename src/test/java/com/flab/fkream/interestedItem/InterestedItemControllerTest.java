package com.flab.fkream.interestedItem;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.fkream.item.Item;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.user.User;
import java.time.LocalDateTime;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(InterestedItemController.class)
class InterestedItemControllerTest {

    @MockBean
    InterestedItemService interestedItemService;

    @Autowired
    MockMvc mockMvc;

    User user = User.builder()
        .id(1L)
        .email("test@test.com")
        .password("testpassword")
        .fourteenAgreement(true)
        .adAgreement(true)
        .personalAuthentication(true)
        .gender("Male")
        .phoneNumber("01012345678")
        .name("testuser")
        .build();

    Item item = Item.builder()
        .id(1L)
        .itemName("나이키 에어포스")
        .modelNumber("NK22035")
        .category1("신발")
        .category2("스니커즈")
        .releaseDate(LocalDateTime.now())
        .representativeColor("Black")
        .releasedPrice(10000)
        .build();

    ItemSizePrice itemSizePrice = ItemSizePrice.builder()
        .id(1L)
        .itemId(item.getId())
        .size("250")
        .build();

    InterestedItem interestedItem = InterestedItem.builder()
        .id(1L)
        .itemSizePriceId(itemSizePrice.getId())
        .userId(user.getId())
        .build();

    @Test
    void save() throws Exception {
        //given
        given(interestedItemService.save(interestedItem)).willReturn(1);

        //when then
        mockMvc.perform(post("/interested-items")
                .content(new ObjectMapper().writeValueAsString(interestedItem))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }


    @Test
    void findAllByUserId() throws Exception {
        //given
        given(interestedItemService.findAllByUserId(user.getId())).willReturn(
            Collections.singletonList(interestedItem));

        //when then
        mockMvc.perform(get("/interested-items/users/{id}", user.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].itemSizePriceId").value(interestedItem.getItemSizePriceId()))
            .andExpect(jsonPath("$[0].userId").value(interestedItem.getUserId()));

        then(interestedItemService).should().findAllByUserId(ArgumentMatchers.anyLong());
    }


    @Test
    void delete() throws Exception {
        //given
        Long id = interestedItem.getId();
        given(interestedItemService.delete(id)).willReturn(1);

        //when then
        mockMvc.perform(MockMvcRequestBuilders.delete("/interested-items/{id}", id))
            .andExpect(status().isOk());

        verify(interestedItemService).delete(id);
    }
}