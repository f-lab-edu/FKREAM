package com.flab.fkream.error.exception;

public class NoCardPasswordException extends RuntimeException {

    private static final String ERROR_MESSAGE = "카드 비밀번호가 없습니다.";

    public NoCardPasswordException() {
        super(ERROR_MESSAGE);
    }

}
