package com.flab.fkream.ownedItems;

import com.flab.fkream.error.exception.NoDataFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class OwnedItemService {

    private final OwnedItemMapper ownedItemMapper;

    public int save(OwnedItem ownedItemInfo) {
        return ownedItemMapper.save(ownedItemInfo);
    }

    public OwnedItem findOne(Long ownedItemId) {
        OwnedItem ownedItem = ownedItemMapper.findOne(ownedItemId);
        if (ownedItem == null) {
            log.error("OwnedItem with id {} not found.", ownedItemId);
            throw new NoDataFoundException();
        }
        return ownedItem;
    }

    public List<OwnedItem> findAllByUserId(Long userId) {
        return ownedItemMapper.findAllByUserId(userId);
    }

    public int update(OwnedItem ownedItem) {
        int result = ownedItemMapper.update(ownedItem);
        if (result == 0) {
            log.error("Failed to update OwnedItem with id {}.", ownedItem.getId());
            throw new NoDataFoundException();
        }
        return result;
    }

    public int delete(Long id) {
        int result = ownedItemMapper.delete(id);
        if (result == 0) {
            log.error("Failed to delete OwnedItem with id {}.", id);
            throw new NoDataFoundException();
        }
        return result;
    }


}
