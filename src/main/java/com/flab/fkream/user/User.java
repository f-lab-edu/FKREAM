package com.flab.fkream.user;

import javax.validation.constraints.AssertTrue;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class User {

    private Long id;

    @NotNull(message = "이메일은 필수입니다.")
    private String email;
    @NotNull(message = "비밀번호는 필수입니다.")
    private String password;
    @AssertTrue(message = "동의는 필수입니다.")
    private boolean fourteenAgreement;
    @AssertTrue(message = "동의은 필수입니다.")
    private boolean adAgreement;
    @AssertTrue(message = "동의은 필수입니다.")
    private boolean personalAuthentication;
    @NotNull(message = "성별은 필수입니다.")
    private String gender;
    @NotNull(message = "전화번호는 필수입니다.")
    private String phoneNumber;
    private String profileName;
    @NotNull(message = "이름은 필수입니다.")
    private String name;
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
}
