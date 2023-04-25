package com.flab.fkream.ownedItems;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@WebMvcTest(OwnedItemController.class)
class OwnedItemControllerTest {

    @MockBean
    private OwnedItemService ownedItemService;
    @Autowired
    MockMvc mockMvc;

    OwnedItem ownedItem;

    final String LOGIN_USERS_ID = "LOGIN_USERS_ID";
    MockHttpSession session = new MockHttpSession();

    @BeforeEach
    public void setUp() {
        ownedItem = OwnedItem.builder()
            .id(1L)
            .itemSizePriceId(1L)
            .userId(1L)
            .purchasePrice(50000)
            .build();
    }


    @Test
    @DisplayName("보유상품 등록 테스트")
    public void addOwnedItemTest() throws Exception {
        // given
        given(ownedItemService.save(any(OwnedItem.class))).willReturn(1);
        MockHttpServletRequestBuilder builder =
            post("/owned-items")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(ownedItem));

        // when
        // then
        mockMvc.perform(builder)
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("보유상품 조회 성공 테스트")
    public void findOneTest_success() throws Exception {
        // given
        given(ownedItemService.findOne(anyLong())).willReturn(ownedItem);

        // when
        // then
        mockMvc.perform(get("/owned-item/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.itemSizePriceId").value(1))
            .andExpect(jsonPath("$.userId").value(1))
            .andExpect(jsonPath("$.purchasePrice").value(50000))
            .andDo(print());
    }

    @Test
    @DisplayName("보유 상품 조회 실패 테스트")
    public void findOne_notFound() throws Exception {
        // given
        given(ownedItemService.findOne(anyLong())).willReturn(null);

        // when
        // then
        mockMvc.perform(get("/owned-item/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("유저 ID로 상품 리스트 조회 성공")
    void findAllByUserId_ok() throws Exception {
        //given
        Long userId = 1L;
        List<OwnedItem> ownedItemList = Collections.singletonList(ownedItem);
        given(ownedItemService.findAllByUserId(userId)).willReturn(ownedItemList);

        //when
        //then
        mockMvc.perform(get("/owned-items/users/" + userId)
                .sessionAttr(LOGIN_USERS_ID, userId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].itemSizePriceId").value(1))
            .andExpect(jsonPath("$[0].userId").value(1))
            .andExpect(jsonPath("$[0].purchasePrice").value(50000));
    }


    @Test
    @DisplayName("유저 ID로 보유상품 리스트 조회 실패 - 다른 유저의 경우")
    void findAllByUserId_failure_differentUser() throws Exception {
        // given
        Long userId = 1L;
        Long anotherUserId = 2L;

        session.setAttribute(LOGIN_USERS_ID, userId);

        // when
        // then
        mockMvc.perform(get("/owned-items/users/" + anotherUserId)
                .session(session))
            .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("보유상품 정보 수정 성공")
    void updateOwnedItem_success() throws Exception {
        // given
        Long userId = ownedItem.getUserId();

        OwnedItem updateOwnedItem = OwnedItem.builder()
            .id(ownedItem.getId())
            .userId(userId)
            .itemSizePriceId(2L)
            .purchasePrice(40000).build();

        session.setAttribute(LOGIN_USERS_ID, userId);
        given(ownedItemService.update(any(OwnedItem.class))).willReturn(1);

        // when
        // then
        mockMvc.perform(patch("/owned-items/{id}", updateOwnedItem.getId())
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateOwnedItem)))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("보유상품 정보 수정 실패 - 다른 유저의 경우")
    void updateOwnedItem_failure_differentUser() throws Exception {
        // given
        Long userId = ownedItem.getUserId(); // 1L
        Long otherUserId = 2L;

        OwnedItem updateOwnedItem = OwnedItem.builder()
            .id(ownedItem.getId())
            .userId(userId)
            .itemSizePriceId(otherUserId)
            .purchasePrice(40000).build();

        session.setAttribute(LOGIN_USERS_ID, otherUserId);

        // when
        // then
        mockMvc.perform(patch("/owned-items/1")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateOwnedItem)))
            .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("보유상품 삭제 성공")
    void deleteOwnedItem_success() throws Exception {
        // given
        Long loggedInUserId = 1L;
        Long ownedItemId = 1L;

        session.setAttribute(LOGIN_USERS_ID, loggedInUserId);

        given(ownedItemService.findOne(ownedItemId)).willReturn(
            new OwnedItem(ownedItemId, 1L, 2L, 60000));

        // when, then
        mockMvc.perform(delete("/owned-items/{id}", ownedItemId).session(session))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("보유상품 삭제 실패 - 다른 유저의 경우")
    void deleteOwnedItem_failure_differentUser() throws Exception {
        // given
        Long loggedInUserId = 1L;
        Long anotherUserId = 2L;
        Long ownedItemId = 1L;

        session.setAttribute(LOGIN_USERS_ID, anotherUserId);
        given(ownedItemService.findOne(ownedItemId)).willReturn(
            new OwnedItem(ownedItemId, loggedInUserId, 2L, 60000));

        // when, then
        mockMvc.perform(delete("/owned-items/{id}", ownedItemId).session(session))
            .andExpect(status().isForbidden());
    }
}