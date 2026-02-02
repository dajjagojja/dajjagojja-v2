package com.multi.travel.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : GlobalExceptionHandler
 * @since : 26. 2. 2. 월요일
 **/


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiExceptionDto> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(new ApiExceptionDto(errorCode));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionDto> handleUnexpected(Exception e) {
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(new ApiExceptionDto(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}