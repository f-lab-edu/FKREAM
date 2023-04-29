package com.flab.fkream.notification;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotificationMapper {

    void save(Notification notification);
}
