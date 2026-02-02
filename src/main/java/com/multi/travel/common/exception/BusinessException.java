package com.multi.travel.common.exception;

import lombok.Getter;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : BusinessException
 * @since : 26. 2. 2. 월요일
 **/


@Getter
public abstract class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    protected BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}