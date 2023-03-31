package com.flab.fkream.salesAccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

import com.flab.fkream.users.Users;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SalesAccount {

	private Long id;
	private Users users;
	private String bankName;
	private String accountNumber;
	private String accountHolder;
	private LocalDateTime createdAt;

}
