package com.flab.fkream.ownedItems;

import com.flab.fkream.error.exception.ForbiddenException;
import com.flab.fkream.utils.SessionUtil;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OwnedItemController {

    private final OwnedItemService ownedItemService;

    @PostMapping("/my-items")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOwnedItem(@Valid @RequestBody @NotNull OwnedItem ownedItemInfo) {
        ownedItemService.save(ownedItemInfo);
    }

    @GetMapping("/my-items/{id}")
    public ResponseEntity<OwnedItem> findOne(@PathVariable Long id) {
        OwnedItem ownedItem = ownedItemService.findOne(id);
        if (ownedItem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ownedItem);
    }

    @GetMapping("/my-items")
    @ResponseStatus(HttpStatus.OK)
    public List<OwnedItem> findAllByUserId() {
        Long loggedInUserId = SessionUtil.getLoginUserId();
        return ownedItemService.findAllByUserId(loggedInUserId);
    }


    @PatchMapping("/my-items")
    @ResponseStatus(HttpStatus.OK)
    public void update(@Valid @RequestBody @NotNull OwnedItem ownedItemInfo) {
        Long loggedInUserId = SessionUtil.getLoginUserId();
        if (!ownedItemInfo.getUserId().equals(loggedInUserId)) {
            throw new ForbiddenException();
        }
        ownedItemService.update(ownedItemInfo);
    }

    @DeleteMapping("/my-items/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        Long loggedInUserId = SessionUtil.getLoginUserId();
        if (!ownedItemService.findOne(id).getUserId().equals(loggedInUserId)) {
            throw new ForbiddenException();
        }
        ownedItemService.delete(id);
    }
}
