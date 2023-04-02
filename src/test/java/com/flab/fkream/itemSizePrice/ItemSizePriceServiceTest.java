package com.flab.fkream.itemSizePrice;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemSizePriceServiceTest {

    @Mock
    ItemSizePriceMapper itemSizePriceMapper;

    @InjectMocks
    ItemSizePriceService itemSizePriceService;

    private static final Long ID =1L;
    private static final Long ITEM_ID = 2L;
    private static final String SIZE = "260";

    ItemSizePrice itemSizePriceInfo = ItemSizePrice.builder()
        .id(ID)
        .itemId(ITEM_ID)
        .size(SIZE)
        .build();


    @Test
    void addItemSizePrice() {
        given(itemSizePriceMapper.save(itemSizePriceInfo)).willReturn(1);
        itemSizePriceService.addItemSizePrice(itemSizePriceInfo);
        then(itemSizePriceMapper).should().save(itemSizePriceInfo);
    }

    @Test
    void findOne() {
        given(itemSizePriceMapper.findOne(ID)).willReturn(itemSizePriceInfo);
        assertThat(itemSizePriceService.findOne(ID)).isEqualTo(itemSizePriceInfo);
    }

    @Test
    void findAllByItemId() {
        given(itemSizePriceMapper.findAllByItemId(ITEM_ID)).willReturn(List.of(itemSizePriceInfo));
        assertThat(itemSizePriceService.findAllByItemId(ITEM_ID)).hasSize(1);
    }

    @Test
    void findByItemIdAndSize() {
        given(itemSizePriceMapper.findByItemIdAndSize(ITEM_ID,SIZE)).willReturn(itemSizePriceInfo);
        assertThat(itemSizePriceService.findByItemIdAndSize(ITEM_ID,SIZE)).isEqualTo(itemSizePriceInfo);
    }

    @Test
    void delete() {
        given(itemSizePriceMapper.delete(ID)).willReturn(1);
        itemSizePriceService.delete(ID);
        then(itemSizePriceMapper).should().delete(ID);
        }
}