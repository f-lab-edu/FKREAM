package com.flab.fkream.ownedItems;

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
class OwnedItemServiceTest {

    @Mock
    OwnedItemMapper ownedItemMapper;

    @InjectMocks
    OwnedItemService ownedItemService;

    OwnedItem ownedItem;

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
    void saveTest() {
        // given
        int expectedResult = 1;
        given(ownedItemMapper.save(any(OwnedItem.class))).willReturn(expectedResult);

        // when
        int result = ownedItemService.save(ownedItem);

        // then
        assertEquals(expectedResult, result);
        then(ownedItemMapper).should().save(any(OwnedItem.class));
    }

    @Test
    void findOneTest() {
        // given
        given(ownedItemMapper.findOne(anyLong())).willReturn(ownedItem);

        // when
        OwnedItem foundOwnedItem = ownedItemService.findOne(ownedItem.getId());

        // then
        assertEquals(ownedItem, foundOwnedItem);
        then(ownedItemMapper).should().findOne(anyLong());
    }

    @Test
    void findOneTest_NoDataFoundException() {
        // given
        given(ownedItemMapper.findOne(anyLong())).willReturn(null);

        // then
        assertThrows(NoDataFoundException.class, () -> {
            // when
            ownedItemService.findOne(ownedItem.getId());
        });
        then(ownedItemMapper).should().findOne(anyLong());
    }

    @Test
    void findAllByUserIdTest() {
        // given
        List<OwnedItem> expectedResult = List.of(ownedItem);
        given(ownedItemMapper.findAllByUserId(anyLong())).willReturn(expectedResult);

        // when
        List<OwnedItem> resultList = ownedItemService.findAllByUserId(ownedItem.getUserId());

        // then
        assertEquals(expectedResult, resultList);
        then(ownedItemMapper).should().findAllByUserId(anyLong());
    }

    @Test
    void updateTest() {
        // given
        int expectedResult = 1;
        given(ownedItemMapper.update(any(OwnedItem.class))).willReturn(expectedResult);

        // when
        int result = ownedItemService.update(ownedItem);

        // then
        assertEquals(expectedResult, result);
        then(ownedItemMapper).should().update(any(OwnedItem.class));
    }

    @Test
    void updateTest_NoDataFoundException() {
        // given
        given(ownedItemMapper.update(any(OwnedItem.class))).willReturn(0);

        // then
        assertThrows(NoDataFoundException.class, () -> {
            // when
            ownedItemService.update(ownedItem);
        });
        then(ownedItemMapper).should().update(any(OwnedItem.class));
    }

    @Test
    void deleteTest() {
        // given
        int expectedResult = 1;
        given(ownedItemMapper.delete(anyLong())).willReturn(expectedResult);

        // when
        int result = ownedItemService.delete(ownedItem.getId());

        // then
        assertEquals(expectedResult, result);
        then(ownedItemMapper).should().delete(anyLong());
    }

    @Test
    void deleteTest_NoDataFoundException() {
        // given
        given(ownedItemMapper.delete(anyLong())).willReturn(0);

        // then
        assertThrows(NoDataFoundException.class, () -> {
            // when
            ownedItemService.delete(ownedItem.getId());
        });
        then(ownedItemMapper).should().delete(anyLong());
    }

}