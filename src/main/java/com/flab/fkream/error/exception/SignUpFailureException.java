package com.flab.fkream.error.exception;

public class SignUpFailureException extends RuntimeException{

    private static final String MESSAGE = "회원가입이 실패했습니다. 다시 시도해주세요.";
    public SignUpFailureException(){
        super(MESSAGE);
    }

}

