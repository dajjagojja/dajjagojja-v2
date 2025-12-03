package com.multi.travel.common.exception;

/**
 * Please explain the class!!!
 *
 * @author : Kim hayeon
 * @filename : EmailDuplicateException
 * @since : 2025. 12. 4. 목요일
 */

public class EmailDuplicateException extends RuntimeException {
    public EmailDuplicateException(String email) {
        super("이미 등록된 이메일입니다: " + email);
    }
}