package com.multi.travel.common.exception;

/**
 * Please explain the class!!!
 *
 * @author : Kim hayeon
 * @filename : InvalidCredentialsExxception
 * @since : 2025. 12. 4. 목요일
 */

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("이메일 또는 비밀번호가 일치하지 않습니다.");
    }
}