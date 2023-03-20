package com.flab.fkream.users;

import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class Users {

    private int id;
    private final String email;
    private final String password;

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

    public Users(String email, String password, String gender, String phoneNumber, String profileName, String name, String rank, String profileImgName, String profileImgUrl, String profileImgOriginName, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.profileName = profileName;
        this.name = name;
        this.rank = rank;
        this.profileImgName = profileImgName;
        this.profileImgUrl = profileImgUrl;
        this.profileImgOriginName = profileImgOriginName;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
