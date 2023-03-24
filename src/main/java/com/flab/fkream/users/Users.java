package com.flab.fkream.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Users {

	private Long id;
	private String email;
	private String password;
	private boolean fourteenAgreement;
	private boolean adAgreement;
	private boolean personalAuthentication;
	private String gender;
	private String phoneNumber;
	private String profileName;
	private String name;
	private String rank;
	private String profileImgName;
	private String profileImgUrl;
	private String profileImgOriginName;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

}