package com.flab.fkream.error.exception;

public class DuplicatedEmailException extends RuntimeException {

    private static final String ERROR_MESSAGE = "중복된 이메일입니다.";

    public DuplicatedEmailException() {
        super(ERROR_MESSAGE);
    }
}
