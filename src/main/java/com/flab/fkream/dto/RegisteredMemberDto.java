package com.flab.fkream.dto;

import com.flab.fkream.domain.Authority;
import com.flab.fkream.domain.Member;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.security.crypto.password.PasswordEncoder;

public record RegisteredMemberDto(
    @NotBlank(message = "유저 이름은 필수 입력 항목입니다")
    @Size(min = 4, max = 50, message = "유저 이름은 4자에서 50자 사이여야 합니다")
    String username,

    @NotBlank(message = "비밀번호는 필수 항목입니다")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "비밀번호는 최소 8자 이상이어야 하며, 알파벳 대문자, 소문자, 숫자, 특수문자(@$!%*?&)를 포함해야 합니다")
    String password,

    @NotBlank(message = "이름은 필수 항목입니다")
    String name,

    @NotBlank(message = "휴대폰번호는 필수 항목입니다")
    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "올바른 휴대전화 번호 형식이 아닙니다")
    String phoneNumber,

    @NotBlank(message = "생년월일은 필수 항목입니다")
    String birthday,

    @NotBlank(message = "성별은 필수 항목입니다")
    String gender
) {

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
            .username(this.username())
            .password(passwordEncoder.encode(this.password()))
            .name(this.name())
            .phoneNumber(this.phoneNumber())
            .birthday(this.birthday())
            .gender(this.gender())
            .authority(Authority.ROLE_USER)
            .build();
    }
}
