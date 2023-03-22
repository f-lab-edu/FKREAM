package com.flab.fkream.salesAccount;

import com.flab.fkream.users.Users;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
@ToString
public class SalesAccount {

    private Long id;
    private final Users users;
    private final String bankName;
    private final String accountNumber;
    private final String accountHolder;
    private final LocalDateTime createdAt;

}
