package com.flab.fkream.salesAccount;

import com.flab.fkream.error.exception.NotOwnedDataException;
import com.flab.fkream.utils.SessionUtil;
import javax.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sales-accounts")
public class SalesAccountController {

    private final SalesAccountService salesAccountService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody @NonNull SalesAccount salesAccountInfo) {
        salesAccountService.save(salesAccountInfo);
    }


    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public SalesAccount findByUserId() {
        SalesAccount salesAccount = salesAccountService.findByUserId();
        return salesAccount;
    }

    @PatchMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody SalesAccount salesAccountInfo) {
        if (salesAccountInfo.getUserId() != SessionUtil.getLoginUserId()) {
            throw new NotOwnedDataException();
        }
        salesAccountService.update(salesAccountInfo);
    }

    @DeleteMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void delete() {
        salesAccountService.deleteById();
    }

}
