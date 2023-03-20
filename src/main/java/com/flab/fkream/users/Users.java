package com.flab.fkream.users;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@Getter
public class Users {

    private int id;
    private final String email;
    private final String password;

    private final String gender;
    private final String phone_number;

    private final String profile_name;
    private final String name;
    private final String rank;

    private final String profile_img_name;

    private final String profile_img_url;
    private final String profile_img_origin_name;

    private final LocalDateTime created_at;
    private final LocalDateTime modified_at;

    public Users(String email, String password, String gender, String phone_number,String profile_name, String name, String rank, String profile_img_name, String img_url, String profile_img_origin_name, LocalDateTime create_at, LocalDateTime modified_at) {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.phone_number = phone_number;
        this.profile_name = profile_name;
        this.name = name;
        this.rank = rank;
        this.profile_img_name = profile_img_name;
        this.profile_img_url = img_url;
        this.profile_img_origin_name = profile_img_origin_name;
        this.created_at = create_at;
        this.modified_at = modified_at;
    }
}
