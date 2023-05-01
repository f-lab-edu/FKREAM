package com.flab.fkream.notification;


import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NotificationMapper {

    int save(Notification notification);

    int saveAll(@Param("notifications")List<Notification> notifications);

    List<Notification> findByUserId(Long userId);

    Notification findById(Long id);

    int delete(Long id);
}
