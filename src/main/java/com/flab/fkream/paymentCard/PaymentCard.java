package com.flab.fkream.paymentCard;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class PaymentCard {

    private Long id;
    private final Users users;
    private final String cardCompany;
    private final String cardNumber;
    private final LocalDate  expiration;
    private final String cardPw;
    private final LocalDateTime createdAt;

    public PaymentCard(Users users, String cardCompany, String cardNumber, LocalDate expiration, String cardPw, LocalDateTime createdAt) {
        this.users = users;
        this.cardCompany = cardCompany;
        this.cardNumber = cardNumber;
        this.expiration = expiration;
        this.cardPw = cardPw;
        this.createdAt = createdAt;
    }
}
