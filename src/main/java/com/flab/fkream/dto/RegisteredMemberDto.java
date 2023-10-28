package com.flab.fkream.dto;

import com.flab.fkream.domain.Authority;
import com.flab.fkream.domain.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

public record RegisteredMemberDto(String username, String password, String name, String phoneNumber,
                                  String birthday, String gender) {

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
