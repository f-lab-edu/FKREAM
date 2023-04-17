package com.flab.fkream.salesAccount;

import java.util.List;
import javax.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // salesAccount 추가
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody @NonNull SalesAccount salesAccountInfo) {
        salesAccountService.save(salesAccountInfo);
    }

    // salesAccount 리스트 조회
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<SalesAccount> findAll() {
        return salesAccountService.findAll();
    }

    // salesAccount 단건 조회
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SalesAccount findOne(@PathVariable Long id) {
        return salesAccountService.findById(id);
    }

    // salesAccount 수정
    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody SalesAccount salesAccountInfo) {
        salesAccountService.update(salesAccountInfo);
    }

    // salesAccount 삭제
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        salesAccountService.deleteById(id);
    }

}
