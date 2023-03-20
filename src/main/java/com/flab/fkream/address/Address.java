package com.flab.fkream.address;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
@ToString
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



}
