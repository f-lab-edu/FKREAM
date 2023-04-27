package com.flab.fkream.myItems;

import com.flab.fkream.error.exception.NoDataFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MyItemService {

    private final MyItemMapper myItemMapper;

    public int save(MyItem myItemInfo) {
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
