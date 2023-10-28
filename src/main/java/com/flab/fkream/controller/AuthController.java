package com.flab.fkream.controller;

import com.flab.fkream.dto.LoginRequestDto;
import com.flab.fkream.dto.MemberDto;
import com.flab.fkream.dto.MemberResponseDto;
import com.flab.fkream.dto.TokenDto;
import com.flab.fkream.dto.TokenRequestDto;
import com.flab.fkream.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<MemberResponseDto> register(
        @RequestBody MemberDto memberRequestDto) {
        return ResponseEntity.ok(authService.register(memberRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
}
