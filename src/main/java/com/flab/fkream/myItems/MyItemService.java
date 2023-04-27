package com.flab.fkream.myItems;

import com.flab.fkream.error.exception.NoDataFoundException;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.itemSizePrice.ItemSizePriceMapper;
import com.flab.fkream.user.User;
import com.flab.fkream.user.UserMapper;
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
        User user = Optional.ofNullable(userMapper.findOne(myItemInfo.getUserId()))
            .orElseThrow(() -> new NotFoundException("해당 유저를 찾을 수 없습니다."));
        ItemSizePrice itemSizePrice = Optional.ofNullable(
            itemSizePriceMapper.findOne(myItemInfo.getItemSizePriceId())
        ).orElseThrow(() -> new NotFoundException("해당 아이템사이즈가격을 찾을 수 없습니다."));

        return myItemMapper.save(myItemInfo);
    }

    public MyItem findOne(Long ownedItemId) {
        MyItem myItem = myItemMapper.findOne(ownedItemId);
        if (myItem == null) {
            log.error("MyItem with id {} not found.", ownedItemId);
            throw new NoDataFoundException();
        }
        return myItem;
    }

    public List<MyItem> findAllByUserId(Long userId) {
        return myItemMapper.findAllByUserId(userId);
    }

    public int update(MyItem myItem) {
        int result = myItemMapper.update(myItem);
        if (result == 0) {
            log.error("Failed to update MyItem with id {}.", myItem.getId());
            throw new NoDataFoundException();
        }
        return result;
    }

    public int delete(Long id) {
        int result = myItemMapper.delete(id);
        if (result == 0) {
            log.error("Failed to delete MyItem with id {}.", id);
            throw new NoDataFoundException();
        }
        return result;
    }


}
