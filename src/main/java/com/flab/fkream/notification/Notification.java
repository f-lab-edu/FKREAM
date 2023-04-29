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
  private final User user;
    private final InterestedItem interestedItem;
    private final NotificationType notificationType;
    private final LocalDateTime createdAt;

}
