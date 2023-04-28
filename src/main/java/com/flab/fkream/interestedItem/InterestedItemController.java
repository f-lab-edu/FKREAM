package com.flab.fkream.interestedItem;

import com.flab.fkream.error.exception.ForbiddenException;
import com.flab.fkream.utils.SessionUtil;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InterestedItemController {

    private final InterestedItemService interestedItemService;

    @PostMapping("/interested-items")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody InterestedItem interestedItemInfo) {
        interestedItemService.save(interestedItemInfo);
    }

    @GetMapping("/interested-items")
    @ResponseStatus(HttpStatus.OK)
    public List<InterestedItem> findAllByUserId() {
        Long loginUserId = SessionUtil.getLoginUserId();
        return interestedItemService.findAllByUserId(loginUserId);
    }

    @DeleteMapping("/interested-items")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestBody InterestedItem interestedItemInfo) {
        Long loginUserId = SessionUtil.getLoginUserId();
        Long userId = interestedItemInfo.getUserId();
        Long itemSizePriceId = interestedItemInfo.getItemSizePriceId();
        if (!Objects.equals(loginUserId, userId)) {
            throw new ForbiddenException();
        }
        interestedItemService.delete(userId, itemSizePriceId);
    }
}
