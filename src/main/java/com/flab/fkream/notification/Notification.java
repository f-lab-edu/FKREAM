package com.flab.fkream.notification;

import com.flab.fkream.interestedItem.InterestedItem;
import com.flab.fkream.user.User;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
@ToString
public class Notification {

    private Long id;
    private Long userId;
    private Long itemId;
    private Long dealId;
    private String title;
    private String context;
    private NotificationType notificationType;
    private LocalDateTime createdAt;
}
