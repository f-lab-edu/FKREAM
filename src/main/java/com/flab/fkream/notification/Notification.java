package com.flab.fkream.notification;

import java.time.LocalDateTime;

public class Notification {
    private Long id;
    private final Users users;
    private final InterestedItem interestedItem;
    private final NotificationType notificationType;
    private final LocalDateTime createdAt;

    public Notification(Users users, InterestedItem interestedItem, NotificationType notificationType, LocalDateTime createdAt) {a
        this.users = users;
        this.interestedItem = interestedItem;
        this.notificationType = notificationType;
        this.createdAt = createdAt;
    }
}
