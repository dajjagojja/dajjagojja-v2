package com.multi.travel.common.exception;

/**
 * Please explain the class!!!
 *
 * @author : Kim hayeon
 * @filename : MemberNotFoundException
 * @since : 2025. 12. 4. 목요일
 */

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String email) {
        super("해당 사용자를 찾을 수 없습니다: " + email);
    }
}
