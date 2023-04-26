package com.flab.fkream.notification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDto {

    private Long id;
    private String title;
    private String context;
    private String dealId;
    private String itemId;
    private NotificationType notificationType;
    private Long itemImgId;
    private String imgUrl;
    private String imgName;
}
