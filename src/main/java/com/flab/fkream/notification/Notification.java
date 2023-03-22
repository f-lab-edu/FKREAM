package com.flab.fkream.notification;

import com.flab.fkream.interestedItem.InterestedItem;
import com.flab.fkream.users.Users;
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
    private final Users users;
    private final InterestedItem interestedItem;
    private final NotificationType notificationType;
    private final LocalDateTime createdAt;

}
