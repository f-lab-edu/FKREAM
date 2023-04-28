package com.flab.fkream.error.exception;

public class NoRequestHigherPriceThenImmediatePurchaseException extends
    RuntimeException {

    private static final String MESSAGE = "즉시 구매가 보다 높은 가격으로 설정할 수 없습니다.";

    public NoRequestHigherPriceThenImmediatePurchaseException() {
        super(MESSAGE);
    }

}
