package com.flab.fkream.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Users {

    private Long id;
    private final String email;
    private final String password;

    private final boolean fourteenAgreement;
    private final boolean adAgreement;
    private final boolean personalAuthentication;

    private final String gender;
    private final String phoneNumber;

    private final String profileName;
    private final String name;
    private final String rank;



    private final String profileImgName;

    private final String profileImgUrl;
    private final String profileImgOriginName;

    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

}
