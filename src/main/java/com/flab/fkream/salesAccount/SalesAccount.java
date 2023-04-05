package com.flab.fkream.salesAccount;


import com.flab.fkream.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SalesAccount {

	private Long id;
	private User user;
	private String bankName;
	private String accountNumber;
	private String accountHolder;
	private LocalDateTime createdAt;


}
