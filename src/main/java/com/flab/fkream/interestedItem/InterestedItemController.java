package com.flab.fkream.interestedItem;

import com.flab.fkream.error.exception.ForbiddenException;
import com.flab.fkream.utils.SessionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InterestedItemController {

    private final InterestedItemService interestedItemService;

    @PostMapping("/interested-items")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody InterestedItem interestedItemInfo) {
        Long loginUserId = SessionUtil.getLoginUserId();
        if (!Objects.equals(loginUserId, interestedItemInfo.getUserId())) {
            throw new ForbiddenException();
        }
        interestedItemService.save(interestedItemInfo);
    }

    @GetMapping("/interested-items")
    @ResponseStatus(HttpStatus.OK)
    public PageInfo<InterestedItem> findAllByUserId(@RequestParam int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        Long loginUserId = SessionUtil.getLoginUserId();
        PageHelper.startPage(pageNum, pageSize);
        return PageInfo.of(interestedItemService.findAllByUserId(loginUserId));
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
