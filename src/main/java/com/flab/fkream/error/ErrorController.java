package com.flab.fkream.error;


import com.flab.fkream.error.exception.DuplicateEmailException;
import com.flab.fkream.error.exception.MapperException;
import com.flab.fkream.error.exception.NoLoginException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

@RestControllerAdvice
public class ErrorController {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateEmailException.class)
    public ErrorMsg handleDuplicateEmailException(DuplicateEmailException e){
        return new ErrorMsg(e.getMessage(), e.getClass().getSimpleName());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NoLoginException.class)
    public ErrorMsg handleNoLogin(NoLoginException e){
        return new ErrorMsg(e.getMessage(), e.getClass().getSimpleName());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MapperException.class)
    public ErrorMsg handleMapperException(MapperException e){
        return new ErrorMsg(e.getMessage(), e.getClass().getSimpleName());
    }
}
