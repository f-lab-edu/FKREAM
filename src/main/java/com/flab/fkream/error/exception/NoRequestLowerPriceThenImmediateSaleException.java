package com.flab.fkream.error.exception;

public class NoRequestLowerPriceThenImmediateSaleException extends
    RuntimeException {

    private static final String MESSAGE = "즉시 판매가 보다 낮은 가격으로 설정할 수 없습니다.";

    public NoRequestLowerPriceThenImmediateSaleException(){
        super(MESSAGE);
    }

}
