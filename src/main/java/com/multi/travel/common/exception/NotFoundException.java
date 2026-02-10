package com.multi.travel.common.exception;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : NotFoundException
 * @since : 26. 2. 9. 월요일
 **/


public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}