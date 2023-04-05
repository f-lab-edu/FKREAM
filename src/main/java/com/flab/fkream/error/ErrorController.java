package com.flab.fkream.error;


import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity handleMapperException(DataAccessException e) {
        return ErrorMsg.toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }
}
