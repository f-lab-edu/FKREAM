package com.flab.fkream.dto;


public record TokenDto(String grantType, String accessToken, String refreshToken,
                       Long accessTokenExpiresIn) {

}
