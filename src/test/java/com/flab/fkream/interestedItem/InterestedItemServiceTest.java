package com.flab.fkream.interestedItem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.flab.fkream.item.Item;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.user.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InterestedItemServiceTest {

    @Mock
    InterestedItemMapper interestedItemMapper;

    @InjectMocks
    InterestedItemService interestedItemService;

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
    void addInterestedItem() {
        //given
        given(interestedItemMapper.save(interestedItem)).willReturn(1);

        //when
        int result = interestedItemService.save(interestedItem);

        //then
        then(interestedItemMapper).should().save(ArgumentMatchers.any(InterestedItem.class));
        assertThat(result).isEqualTo(1);
    }

    @Test
    void findAllByUserId() {
        //given
        List<InterestedItem> interestedItems = new ArrayList<>();
        interestedItems.add(interestedItem);
        given(interestedItemMapper.findAllByUserId(1L)).willReturn(interestedItems);

        //when
        List<InterestedItem> result = interestedItemMapper.findAllByUserId(user.getId());

        //then
        then(interestedItemMapper).should().findAllByUserId(ArgumentMatchers.anyLong());
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(interestedItem);
    }

    @Test
    void delete() {
        //given
        Long userId = interestedItem.getUserId();
        Long itemSizePriceId = interestedItem.getItemSizePriceId();
        given(interestedItemMapper.deleteById(userId,
            itemSizePriceId)).willReturn(1);

        //when
        int result = interestedItemService.delete(userId,
            itemSizePriceId);

        //then
        then(interestedItemMapper).should()
            .deleteById(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
        assertThat(result).isEqualTo(1);
    }
}