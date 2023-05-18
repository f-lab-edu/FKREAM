package com.flab.fkream.error.exception;

public class NoMatchDealTypeException  extends RuntimeException{

    private static final String MESSAGE = "구매 및 판매 설정 오류 발생";

    public NoMatchDealTypeException(){
        super(MESSAGE);
    }

}
