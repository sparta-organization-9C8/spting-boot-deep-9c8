package com.example.loginlivesession2.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

//    public CustomException(ErrorCode errorCode) {
//        this.errorCode = errorCode;
//    }


}
