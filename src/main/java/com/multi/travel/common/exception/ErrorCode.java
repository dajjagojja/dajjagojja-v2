package com.multi.travel.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : ErrorCode
 * @since : 26. 2. 2. 월요일
 **/


public enum ErrorCode {

    // AUTH
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "AUTH_001", "아이디 또는 비밀번호가 올바르지 않습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH_002", "토큰이 만료되었습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH_003", "인증이 필요합니다."),

    // MEMBER
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_001", "회원을 찾을 수 없습니다."),
    EMAIL_DUPLICATE(HttpStatus.CONFLICT, "MEMBER_002", "이미 사용 중인 이메일입니다."),
    MEMBER_DISABLED(HttpStatus.FORBIDDEN, "MEMBER_003", "비활성화된 계정입니다."),

    // PLACE_SEED
    SEED_API_FETCH_FAILED(HttpStatus.BAD_GATEWAY, "SEED_001", "외부 관광 API 호출에 실패했습니다."),

    // PLACE
    TOUR_SPOT_NOT_FOUND(HttpStatus.NOT_FOUND, "PLACE_001", "관광지를 찾을 수 없습니다."),
    ACCOMMODATION_NOT_FOUND(HttpStatus.NOT_FOUND, "PLACE_002", "숙소를 찾을 수 없습니다."),

    // COMMON
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_001", "서버 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}