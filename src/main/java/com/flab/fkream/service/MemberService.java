package com.flab.fkream.service;

import com.flab.fkream.dto.MemberResponseDto;
import com.flab.fkream.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberResponseDto findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
            .map(MemberResponseDto::of)
            .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }

    public MemberResponseDto findMemberByUsername(String username) {
        return memberRepository.findByUsername(username)
            .map(MemberResponseDto::of)
            .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }
}
