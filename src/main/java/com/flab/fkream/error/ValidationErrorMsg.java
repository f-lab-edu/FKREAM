package com.flab.fkream.error;

import com.flab.fkream.utils.HttpRequestUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

@SuperBuilder
@Getter
public class ValidationErrorMsg extends ErrorMsg {

  private static final String MESSAGE = "필수 데이터를 입력해야합니다.";

  private final List<String> validationMessage;

  public static ResponseEntity toResponseVEntity(HttpStatus httpStatus, List<String> message) {
    return ResponseEntity.status(httpStatus)
        .body(
            ValidationErrorMsg.builder()
                .error(httpStatus.name())
                .code(httpStatus.value())
                .message(MESSAGE)
                .validationMessage(message)
                .path(HttpRequestUtils.getRequest().getRequestURI())
                .build());
  }
}
