package com.flab.fkream.exception;

import org.springframework.http.HttpStatus;

public class FkreamException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "일시적 접속 오류, 문의 바람";
    private final HttpStatus status;


    public FkreamException() {
        super(DEFAULT_MESSAGE);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public FkreamException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
    }

    public FkreamException(final String message, final Throwable cause ,final HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

}
