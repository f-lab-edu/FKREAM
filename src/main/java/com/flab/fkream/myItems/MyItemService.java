package com.flab.fkream.myItems;

import com.flab.fkream.error.exception.NoDataFoundException;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.mapper.ItemSizePriceMapper;
import com.flab.fkream.mapper.MyItemMapper;
import com.flab.fkream.mapper.UserMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MyItemService {

    private final UserMapper userMapper;
    private final ItemSizePriceMapper itemSizePriceMapper;
    private final MyItemMapper myItemMapper;

    public int save(MyItem myItemInfo) throws NotFoundException {
        ItemSizePrice itemSizePrice = Optional.ofNullable(
            itemSizePriceMapper.findOne(myItemInfo.getItemSizePriceId())
        ).orElseThrow(() -> new NotFoundException("해당 아이템사이즈가격을 찾을 수 없습니다."));

        return myItemMapper.save(myItemInfo);
    }

    public MyItem findOne(Long myItemId) {
        MyItem myItem = myItemMapper.findOne(myItemId);
        if (myItem == null) {
            throw new NoDataFoundException();
        }
        return myItem;
    }

    public List<MyItem> findAllByUserId(Long userId) {
        return myItemMapper.findAllByUserId(userId);
    }

    public void update(MyItem myItem) throws NotFoundException {
        ItemSizePrice itemSizePrice = Optional.ofNullable(
            itemSizePriceMapper.findOne(myItem.getItemSizePriceId())
        ).orElseThrow(() -> new NotFoundException("해당 아이템사이즈가격을 찾을 수 없습니다."));
        myItemMapper.update(myItem);
    }

    public void delete(Long id) {
        myItemMapper.delete(id);
    }

}
