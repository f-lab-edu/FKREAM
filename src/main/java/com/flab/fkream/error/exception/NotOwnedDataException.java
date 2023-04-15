package com.flab.fkream.error.exception;

public class NotOwnedDataException extends RuntimeException{

    private static final String ERROR_MESSAGE = "해당하는 데이터는 요청자의 데이터가 아닙니다.";

    public NotOwnedDataException() {
        super(ERROR_MESSAGE);
    }
}
