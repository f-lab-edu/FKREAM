package com.flab.fkream.mapper;


import com.flab.fkream.notification.Notification;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotificationMapper {

    void save(Notification notification);
}
