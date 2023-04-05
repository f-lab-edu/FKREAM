package com.flab.fkream.error;

import com.flab.fkream.utils.HttpRequestUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;import java.util.List;

@Getter
@SuperBuilder
public class ErrorMsg {

  private final LocalDateTime timestamp = LocalDateTime.now();

  private final String error;
  private final int code;
  private final String message;
  private final String path;

  public static ResponseEntity<ErrorMsg> toResponseEntity(HttpStatus httpStatus, Exception e) {
    return ResponseEntity.status(httpStatus)
        .body(
            ErrorMsg.builder()
                .error(httpStatus.name())
                .code(httpStatus.value())
                .message(e.getMessage())
                .path(HttpRequestUtils.getRequest().getRequestURI())
                .build());
  }
}
