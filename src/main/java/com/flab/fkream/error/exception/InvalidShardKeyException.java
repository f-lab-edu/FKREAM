package com.flab.fkream.error.exception;

public class InvalidShardKeyException extends RuntimeException {

    private static final String MESSAGE = "샤딩 키가 룰에 맞지 않습니다.";

    public InvalidShardKeyException() {
        super(MESSAGE);
    }
}
