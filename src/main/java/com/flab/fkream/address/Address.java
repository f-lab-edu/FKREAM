package com.flab.fkream.address;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Address {

    private Long id;
    private final Users users;
    private final String name;
    private final String phoneNumber;
    private final String zipcode;
    private final String detail1;
    private final String detail2;

    private final boolean defaultAddress;
    private final LocalDateTime createdAt;


    public Address(Users users, String name, String phoneNumber, String zipcode, String detail1, String detail2, boolean defaultAddress, LocalDateTime createdAt) {
        this.users = users;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.zipcode = zipcode;
        this.detail1 = detail1;
        this.detail2 = detail2;
        this.defaultAddress = defaultAddress;
        this.createdAt = createdAt;
    }
}
