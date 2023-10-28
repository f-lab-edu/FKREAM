package com.flab.fkream.dto;

import com.flab.fkream.domain.Member;

public record MemberResponseDto(String username) {

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member.getUsername());
    }
}
