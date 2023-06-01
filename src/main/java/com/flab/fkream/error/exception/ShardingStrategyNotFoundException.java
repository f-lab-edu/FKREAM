package com.flab.fkream.error.exception;

public class ShardingStrategyNotFoundException extends
    RuntimeException {

    private static final String ERROR_MESSAGE = "=샤딩 전략을 찾을 수 없습니다.";

    public ShardingStrategyNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
