package com.flab.fkream.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public record LoginRequestDto(
    String username,
    String password
) {

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(this.username, this.password);
    }
}
