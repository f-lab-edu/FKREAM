package com.flab.fkream.error.exception;

public class MapperException extends RuntimeException{

    private static final String MESSAGE = "서버 에러입니다. 다시 시도해주세요.";

    public MapperException(Exception e){
        super(MESSAGE, e);
    }
}
