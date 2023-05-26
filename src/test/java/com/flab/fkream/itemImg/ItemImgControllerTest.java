package com.flab.fkream.itemImg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.flab.fkream.brand.Brand;
import com.flab.fkream.item.Item;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemImgController.class)
@ActiveProfiles({"test"})
class ItemImgControllerTest {

    @MockBean
    ItemImgService itemImgService;

    @Autowired
    MockMvc mockMvc;

    Item itemInfo =
        Item.builder()
            .itemName("나이키 에어포스")
            .modelNumber("NK22035")
            .categoryId(1L)
            .detailedCategoryId(2L)
            .releaseDate(LocalDate.now())
            .representativeColor("Black")
            .releasedPrice(10000)
            .brand(new Brand())
            .build();

    ItemImg itemImgInfo =
        ItemImg.builder()
            .item(itemInfo)
            .imgUrl("test")
            .imgName("test")
            .originName("test_origin")
            //.isRepresentativeImg(true)
            .build();

    @Test
    void addItemImg() throws Exception {
        doNothing().when(itemImgService).addItemImg(itemImgInfo);
        mockMvc
            .perform(
                post("/itemImgs")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(getContent(itemImgInfo)))
            .andExpect(status().isOk());
    }

    @Test
    void findImagesByItemId() throws Exception {
        given(itemImgService.findImagesByItemId(1L)).willReturn(List.of(itemImgInfo));
        mockMvc
            .perform(get("/itemImgs/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void deleteItemImg() throws Exception {
        doNothing().when(itemImgService).delete(1L);
        mockMvc
            .perform(delete("/itemImgs/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    private String getContent(ItemImg itemImgInfo) throws JsonProcessingException {
        return new ObjectMapper().registerModule(new JavaTimeModule())
            .writeValueAsString(itemImgInfo);
    }
}
