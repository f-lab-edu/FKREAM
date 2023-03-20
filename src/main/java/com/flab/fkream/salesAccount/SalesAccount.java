package com.flab.fkream.salesAccount;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SalesAccount {

    private Long id;
    private final Users users;

    private final String bankName;
    private final String accountNumber;
    private final String accountHolder;
    private final LocalDateTime createdAt;

    public SalesAccount(Users users, String bankName, String accountNumber, String accountHolder, LocalDateTime createdAt) {
        this.users = users;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.createdAt = createdAt;
    }
}
