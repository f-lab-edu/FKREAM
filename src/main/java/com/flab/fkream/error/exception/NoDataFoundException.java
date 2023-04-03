package com.flab.fkream.error.exception;

public class NoDataFoundException extends RuntimeException {

    private static final String MESSAGE = "해당하는 데이터가 없습니다.";

    public NoDataFoundException(){
        super(MESSAGE);
    }

}