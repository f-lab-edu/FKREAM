package com.flab.fkream.error;




import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.flab.fkream.error.exception.NoCardPasswordException;
import com.flab.fkream.error.exception.NoDataFoundException;
import com.flab.fkream.error.exception.NotOwnedDataException;
import org.springframework.dao.DataAccessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.flab.fkream.error.exception.DuplicatedEmailException;
import com.flab.fkream.error.exception.LoginFailureException;
import com.flab.fkream.error.exception.LoginRequiredException;
import com.flab.fkream.error.exception.SignUpFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorController {


    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity handleInvalidFormatException(InvalidFormatException e) {
        return ErrorMsg.toResponseEntity(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler({NoDataFoundException.class, NoCardPasswordException.class})
    public ResponseEntity handleDataException(Exception e){
        return ErrorMsg.toResponseEntity(HttpStatus.BAD_REQUEST, e);
    }
    @ExceptionHandler(NotOwnedDataException.class)
    public ResponseEntity handleNotOwnedDataException(NotOwnedDataException e){
        return ErrorMsg.toResponseEntity(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity handleMapperException(DataAccessException e) {
        return ErrorMsg.toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }
    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<ErrorMsg> handleDuplicatedEmailException(DuplicatedEmailException e) {
      return ErrorMsg.toResponseEntity(HttpStatus.CONFLICT, e);
    }

    @ExceptionHandler(LoginRequiredException.class)
    public ResponseEntity<ErrorMsg> handleLoginRequiredException(LoginRequiredException e) {
      return ErrorMsg.toResponseEntity(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(LoginFailureException.class)
    public ResponseEntity<ErrorMsg> handleLoginFailureException(LoginFailureException e) {
      return ErrorMsg.toResponseEntity(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity processValidationError(MethodArgumentNotValidException e) {
      List<String> errorMessage =
          e.getBindingResult().getAllErrors().stream()
              .map(
                  (error) ->
                      "field: "
                          + ((FieldError) error).getField()
                          + ", ErrorMessage : "
                          + error.getDefaultMessage())
              .collect(Collectors.toList());
      return ValidationErrorMsg.toResponseVEntity(HttpStatus.BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(SignUpFailureException.class)
    public ResponseEntity<ErrorMsg> handleLoginFailureException(SignUpFailureException e) {
      return ErrorMsg.toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMsg> handleIllegalArgumentException(IllegalArgumentException e) {
        return ErrorMsg.toResponseEntity(HttpStatus.BAD_REQUEST, e);
    }
}
