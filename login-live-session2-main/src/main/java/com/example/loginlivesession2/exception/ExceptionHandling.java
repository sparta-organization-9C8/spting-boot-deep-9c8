package com.example.loginlivesession2.exception;

import lombok.RequiredArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandling {

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> customExceptionHandle(CustomException e) {

        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @RequiredArgsConstructor
    private static class ErrorResponse {
        private final int httpStatus;
        private final String errorCode;
        private final String message;

        public static ResponseEntity toResponseEntity(ErrorCode errorCode) {
            return ResponseEntity
                    .status(errorCode.getHttpStatus()) //400 , 500 ... 이런거를 설정
                    .body(new ErrorResponse(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage()));

        }
    }
}
