package com.flab.fkream.fcm;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FCMTokenMapper {

    int save(Long userId, String token);

    String findByUserId(Long userId);

    int delete(Long userId);
}
