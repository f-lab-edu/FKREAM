package com.flab.fkream.users;

import com.flab.fkream.utils.SHA256Util;
import lombok.*;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Users {


	private Long id;
	@NonNull
	private String email;
	@NonNull
	private String password;
	@NonNull
	private boolean fourteenAgreement;
	@NonNull
	private boolean adAgreement;
	@NonNull
	private boolean personalAuthentication;
	@NonNull
	private String gender;
	@NonNull
	private String phoneNumber;
	private String profileName;
	@NonNull
	private String name;
	private Rank rank = Rank.BRONZE;
	private String profileImgName;
	private String profileImgUrl;
	private String profileImgOriginName;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public boolean hasNullData() {
		return  email == null ||
				password == null ||
				fourteenAgreement == false ||
				adAgreement == false ||
				personalAuthentication == false ||
				gender == null ||
				phoneNumber == null ||
				name == null;
	}

	public enum Rank {
		BRONZE, SILVER, GOLD
	}

}
