package com.multi.travel.common.exception;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : PlaceSeedApiException
 * @since : 26. 2. 2. 월요일
 **/


public class PlaceSeedApiException extends BusinessException {

    public PlaceSeedApiException() {
        super(ErrorCode.SEED_API_FETCH_FAILED);
    }

    public PlaceSeedApiException(Throwable cause) {
        super(ErrorCode.SEED_API_FETCH_FAILED);
        initCause(cause);
    }
}