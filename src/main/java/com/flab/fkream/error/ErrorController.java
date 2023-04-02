package com.flab.fkream.error;


import com.flab.fkream.error.exception.DuplicateEmailException;
import com.flab.fkream.error.exception.MapperException;
import com.flab.fkream.error.exception.NoLoginException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(MapperException.class)
    public ResponseEntity handleMapperException(MapperException e){
        return ErrorMsg.toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }
}
