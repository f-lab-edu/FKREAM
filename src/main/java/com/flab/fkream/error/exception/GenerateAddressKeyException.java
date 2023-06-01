package com.flab.fkream.error.exception;

public class GenerateAddressKeyException extends RuntimeException {

    private static final String MESSAGE = "address 키 생성에 실패했습니다.";

    public GenerateAddressKeyException() {
        super(MESSAGE);
    }

}
