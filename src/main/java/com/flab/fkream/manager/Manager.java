package com.flab.fkream.manager;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Manager {
	private Long id;
	private String employeeNumber;
	private String password;
	private String name;
	private String phoneNumber;
	private String agreement;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
}
