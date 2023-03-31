package com.flab.fkream.itemImg;

import com.flab.fkream.item.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ItemImgServiceTest {

  @Mock ItemImgMapper itemImgMapper;
  @InjectMocks ItemImgService itemImgService;

  Item itemInfo =
      Item.builder()
          .itemName("나이키 에어포스")
          .modelNumber("NK22035")
          .category1("신발")
          .category2("스니커즈")
          .releaseDate(LocalDateTime.now())
          .representativeColor("Black")
          .releasedPrice(10000)
          .build();

  ItemImg itemImgInfo =
      ItemImg.builder()
          .item(itemInfo)
          .imgUrl("test")
          .imgName("test")
          .originName("test_origin")
          .isRepresentativeImg(true)
          .build();

  @Test
  void addItemImg() {
    given(itemImgMapper.save(itemImgInfo)).willReturn(1);
    itemImgService.addItemImg(itemImgInfo);
  }

  @Test
  void findImagesByItemId() {
    given(itemImgMapper.findImagesByItemId(1L)).willReturn(List.of());
    itemImgService.findImagesByItemId(1L);
  }

  @Test
  void delete() {
    given(itemImgMapper.delete(1L)).willReturn(1);
    itemImgService.delete(1L);
  }
}
