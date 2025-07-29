package com.viet.blog_api.hanlder;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.viet.blog_api.constant.ExecptionCode;
import com.viet.blog_api.dto.exception.ExceptionResponse;

@RestControllerAdvice
public class GlobalExceptionHanlder {

    // common runtime exception handler
    @ExceptionHandler(exception = Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {

        ExecptionCode code = ExecptionCode.INTERNAL_SERVER_ERROR;
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();

        // check code and status of exception
        if (e instanceof DisabledException) {
            code = ExecptionCode.ACCOUNT_DISABLED;
            status = HttpStatus.FORBIDDEN.value();
        }

        ExceptionResponse response = ExceptionResponse.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .code(code)
                .build();
        return ResponseEntity.status(status).body(response);
    }
}
