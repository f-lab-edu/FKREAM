package com.flab.fkream.error.exception;

public class LoginFailureException extends RuntimeException {

    private static final String LOGIN_FAILURE = "로그인 실패했습니다.";

    public LoginFailureException() {
        super(LOGIN_FAILURE);
    }
}
