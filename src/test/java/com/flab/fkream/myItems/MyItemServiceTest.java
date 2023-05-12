package com.flab.fkream.myItems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.flab.fkream.error.exception.NoDataFoundException;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.itemSizePrice.ItemSizePriceMapper;
import com.flab.fkream.user.User;
import com.flab.fkream.user.UserMapper;
import java.util.List;
import org.apache.ibatis.javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MyItemServiceTest {

    @Mock
    UserMapper userMapper;
    @Mock
    ItemSizePriceMapper itemSizePriceMapper;
    @Mock
    MyItemMapper myItemMapper;

    @InjectMocks
    MyItemService myItemService;

    User user;
    ItemSizePrice itemSizePrice;
    MyItem myItem;

    @BeforeEach
    public void setUp() {
        user = User.builder()
            .id(1L)
            .build();
        itemSizePrice = ItemSizePrice.builder()
            .id(1L)
            .itemId(1L)
            .build();
        myItem = MyItem.builder()
            .id(1L)
            .itemSizePriceId(1L)
            .userId(1L)
            .purchasePrice(50000)
            .build();
    }

    @Test
    @DisplayName("저장 성공")
    void saveTest() throws NotFoundException {
        // given
        int expectedResult = 1;
        given(userMapper.findOne(anyLong())).willReturn(user);
        given(itemSizePriceMapper.findOne(anyLong())).willReturn(itemSizePrice);
        given(myItemMapper.save(any(MyItem.class))).willReturn(expectedResult);

        // when
        int result = myItemService.save(myItem);

        // then
        assertEquals(expectedResult, result);
        then(myItemMapper).should().save(any(MyItem.class));
    }

    @Test
    @DisplayName("저장 실패 - user null")
    void saveTest_fail_userNull() {
        // given
        given(userMapper.findOne(anyLong())).willReturn(null);

        // when, then
        assertThrows(NotFoundException.class, () -> myItemService.save(myItem));
    }

    @Test
    @DisplayName("저장 실패 - itemSizePrice null")
    void saveTest_fail_itemSizePriceNull() {
        // given
        given(userMapper.findOne(anyLong())).willReturn(user);
        given(itemSizePriceMapper.findOne(anyLong())).willReturn(null);

        // when, then
        assertThrows(NotFoundException.class, () -> myItemService.save(myItem));
    }

    @Test
    void findOneTest() {
        // given
        given(myItemMapper.findOne(anyLong())).willReturn(myItem);

        // when
        MyItem foundMyItem = myItemService.findOne(myItem.getId());

        // then
        assertEquals(myItem, foundMyItem);
        then(myItemMapper).should().findOne(anyLong());
    }

    @Test
    void findOneTest_NoDataFoundException() {
        // given
        given(myItemMapper.findOne(anyLong())).willReturn(null);

        // then
        assertThrows(NoDataFoundException.class, () -> {
            // when
            myItemService.findOne(myItem.getId());
        });
        then(myItemMapper).should().findOne(anyLong());
    }

    @Test
    void findAllByUserIdTest() {
        // given
        List<MyItem> expectedResult = List.of(myItem);
        given(myItemMapper.findAllByUserId(anyLong())).willReturn(expectedResult);

        // when
        List<MyItem> resultList = myItemService.findAllByUserId(myItem.getUserId());

        // then
        assertEquals(expectedResult, resultList);
        then(myItemMapper).should().findAllByUserId(anyLong());
    }

    @Test
    void updateTest() throws NotFoundException {
        // given
        int expectedResult = 1;
        given(myItemMapper.update(any(MyItem.class))).willReturn(expectedResult);

        // when
        int result = myItemService.update(myItem);

        // then
        assertEquals(expectedResult, result);
        then(myItemMapper).should().update(any(MyItem.class));
    }

    @Test
    void updateTest_NoDataFoundException() {
        // given
        given(myItemMapper.update(any(MyItem.class))).willReturn(0);

        // then
        assertThrows(NoDataFoundException.class, () -> {
            // when
            myItemService.update(myItem);
        });
        then(myItemMapper).should().update(any(MyItem.class));
    }

    @Test
    void deleteTest() {
        // given
        int expectedResult = 1;
        given(myItemMapper.delete(anyLong())).willReturn(expectedResult);

        // when
        int result = myItemService.delete(myItem.getId());

        // then
        assertEquals(expectedResult, result);
        then(myItemMapper).should().delete(anyLong());
    }

    @Test
    void deleteTest_NoDataFoundException() {
        // given
        given(myItemMapper.delete(anyLong())).willReturn(0);

        // then
        assertThrows(NoDataFoundException.class, () -> {
            // when
            myItemService.delete(myItem.getId());
        });
        then(myItemMapper).should().delete(anyLong());
    }

}