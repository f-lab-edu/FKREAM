package com.flab.fkream.myItems;

import com.flab.fkream.error.exception.ForbiddenException;
import com.flab.fkream.utils.SessionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyItemController {

    private final MyItemService myItemService;

    @PostMapping("/my-items")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOwnedItem(@Valid @RequestBody @NotNull MyItem myItemInfo)
        throws NotFoundException {
        myItemService.save(myItemInfo);
    }

    @GetMapping("/my-items/{id}")
    public ResponseEntity<MyItem> findOne(@PathVariable Long id) {
        MyItem myItem = myItemService.findOne(id);
        if (myItem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(myItem);
    }

    @GetMapping("/my-items")
    @ResponseStatus(HttpStatus.OK)
    public PageInfo<MyItem> findAllByUserId(@RequestParam int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        Long loggedInUserId = SessionUtil.getLoginUserId();
        PageHelper.startPage(pageNum, pageSize);
        return PageInfo.of(myItemService.findAllByUserId(loggedInUserId));
    }


    @PatchMapping("/my-items")
    @ResponseStatus(HttpStatus.OK)
    public void update(@Valid @RequestBody @NotNull MyItem myItemInfo) throws NotFoundException {
        Long loggedInUserId = SessionUtil.getLoginUserId();
        if (!myItemInfo.getUserId().equals(loggedInUserId)) {
            throw new ForbiddenException();
        }
        myItemService.update(myItemInfo);
    }

    @DeleteMapping("/my-items/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        Long loggedInUserId = SessionUtil.getLoginUserId();
        if (!myItemService.findOne(id).getUserId().equals(loggedInUserId)) {
            throw new ForbiddenException();
        }
        myItemService.delete(id);
    }
}
