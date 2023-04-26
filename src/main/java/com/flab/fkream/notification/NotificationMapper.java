package com.flab.fkream.notification;


import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotificationMapper {

    int save(Notification notification);

    int saveAll(List<Notification> notifications);

    List<Notification> findByUserId(Long userId);

    Notification findById(Long id);

    int delete(Long id);
}
