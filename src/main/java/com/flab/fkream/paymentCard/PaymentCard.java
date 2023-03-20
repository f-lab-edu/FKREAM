package com.flab.fkream.paymentCard;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
@ToString
public class PaymentCard {

    private Long id;
    private final Users users;
    private final String cardCompany;
    private final String cardNumber;
    private final LocalDate  expiration;
    private final String cardPw;
    private final LocalDateTime createdAt;

}
