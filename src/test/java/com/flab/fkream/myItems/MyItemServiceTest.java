package com.flab.fkream.myItems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.flab.fkream.error.exception.NoDataFoundException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MyItemServiceTest {

    @Mock
    MyItemMapper myItemMapper;

    @InjectMocks
    MyItemService myItemService;

    MyItem myItem;

    @BeforeEach
    public void setUp() {
        myItem = MyItem.builder()
            .id(1L)
            .itemSizePriceId(1L)
            .userId(1L)
            .purchasePrice(50000)
            .build();
    }

    @Test
    void saveTest() {
        // given
        int expectedResult = 1;
        given(myItemMapper.save(any(MyItem.class))).willReturn(expectedResult);

        // when
        int result = myItemService.save(myItem);

        // then
        assertEquals(expectedResult, result);
        then(myItemMapper).should().save(any(MyItem.class));
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
    void updateTest() {
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