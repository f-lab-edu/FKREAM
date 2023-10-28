package com.flab.fkream.dto;

import com.flab.fkream.domain.Member;

public record MemberDto(String username, String name, String phoneNumber,
                        String birthday, String gender) {

    public static MemberDto of(Member member) {
        return member.toMemberDto();
    }
}
