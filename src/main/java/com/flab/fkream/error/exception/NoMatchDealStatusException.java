package com.flab.fkream.error.exception;

public class NoMatchDealStatusException extends RuntimeException {

    private static final String MESSAGE = "입찰 및 구매, 판매 중 에러 발생";

    public NoMatchDealStatusException(){
        super(MESSAGE);
    }

    public NoMatchDealStatusException(String customMessage){
        super(customMessage);
    }
}
