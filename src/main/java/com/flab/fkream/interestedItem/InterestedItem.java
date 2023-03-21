package com.flab.fkream.interestedItem;

import java.util.List;

import com.flab.fkream.notification.Notification;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@Builder
public class InterestedItem {
	private Long id;
	private final Long userId;
	private final Long itemSizePriceId;
}
