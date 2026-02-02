package com.multi.travel.common.exception;


import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiExceptionDto {

    private final int status;
    private final String code;
    private final String message;
    private final LocalDateTime timestamp;

    public ApiExceptionDto(ErrorCode errorCode) {
        this.status = errorCode.getStatus().value();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.timestamp = LocalDateTime.now();
    }

    public ApiExceptionDto(HttpStatus status, String message) {
        this.status = status.value();
        this.code = null;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}