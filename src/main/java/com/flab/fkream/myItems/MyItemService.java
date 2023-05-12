package com.flab.fkream.myItems;

import com.flab.fkream.error.exception.ForbiddenException;
import com.flab.fkream.error.exception.NoDataFoundException;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.itemSizePrice.ItemSizePriceMapper;
import com.flab.fkream.user.UserMapper;
import com.flab.fkream.utils.SessionUtil;
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
        Long loggedInUserId = SessionUtil.getLoginUserId();
        if (!myItemInfo.getUserId().equals(loggedInUserId)) {
            throw new ForbiddenException();
        }

        ItemSizePrice itemSizePrice = Optional.ofNullable(
            itemSizePriceMapper.findOne(myItemInfo.getItemSizePriceId())
        ).orElseThrow(() -> new NotFoundException("해당 아이템사이즈가격을 찾을 수 없습니다."));

        return myItemMapper.save(myItemInfo);
    }

    public MyItem findOne(Long myItemId) {
        MyItem myItem = myItemMapper.findOne(myItemId);

        if (myItem == null) {
            log.error("MyItem with id {} not found.", myItemId);
            throw new NoDataFoundException();
        }

        Long loggedInUserId = SessionUtil.getLoginUserId();
        if (!myItem.getUserId().equals(loggedInUserId)) {
            throw new ForbiddenException();
        }

        return myItem;
    }

    public List<MyItem> findAllByUserId(Long userId) {
        Long loggedInUserId = SessionUtil.getLoginUserId();
        if (!userId.equals(loggedInUserId)) {
            throw new ForbiddenException();
        }
        return myItemMapper.findAllByUserId(userId);
    }

    public int update(MyItem myItem) throws NotFoundException {
        Long loggedInUserId = SessionUtil.getLoginUserId();
        if (!myItem.getUserId().equals(loggedInUserId)) {
            throw new ForbiddenException();
        }

        ItemSizePrice itemSizePrice = Optional.ofNullable(
            itemSizePriceMapper.findOne(myItem.getItemSizePriceId())
        ).orElseThrow(() -> new NotFoundException("해당 아이템사이즈가격을 찾을 수 없습니다."));

        int result = myItemMapper.update(myItem);
        if (result == 0) {
            log.error("Failed to update MyItem with id {}.", myItem.getId());
            throw new NoDataFoundException();
        }
        return result;
    }

    public void delete(Long id) {
        findOne(id);

        myItemMapper.delete(id);
    }


}
