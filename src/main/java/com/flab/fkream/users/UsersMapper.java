package com.flab.fkream.users;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UsersMapper {

    String insert = "INSERT INTO USERS(email, password, gender, phone_number, profile_name, name, rank, " +
            "profile_img_name, profile_img_url, profile_img_origin_name, created_at, modified_at) " +
            "Values ( #{users.email}, #{users.password} , #{users.gender}, #{users.phoneNumber}" +
            ",#{users.profileName}, #{users.name}, #{users.rank}, #{users.profileImgName},#{users.profileImgUrl}" +
            ", #{users.profileImgOriginName}, #{users.createdAt}, #{users.modifiedAt})";

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(@Param("users") Users users);


}
