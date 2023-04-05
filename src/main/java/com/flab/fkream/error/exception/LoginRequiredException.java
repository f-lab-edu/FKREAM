package com.flab.fkream.error.exception;

public class LoginRequiredException extends RuntimeException {

    private static final String NEED_LOGIN = "로그인이 필요합니다.";

    public LoginRequiredException() {
        super(NEED_LOGIN);
    }
}
