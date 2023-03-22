package com.flab.fkream.manager;

import java.time.LocalDateTime;
import java.util.List;

import com.flab.fkream.item.Item;

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
public class Manager {
	private Long id;
	private final String employeeNumber;
	private final String password;
	private final String name;
	private final String phoneNumber;
	private final String agreement;
	private final LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
}
