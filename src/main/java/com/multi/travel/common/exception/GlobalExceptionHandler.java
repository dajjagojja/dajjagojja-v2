package com.multi.travel.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionDto> exceptionHandler(Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500
                .body(new ApiExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<ApiExceptionDto> exceptionHandler(RefreshTokenException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiExceptionDto(HttpStatus.UNAUTHORIZED, e.getMessage()));
    }

    @ExceptionHandler(TourSpotNotFoundException.class)
    public ResponseEntity<?> handleTourSpotNotFound(TourSpotNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(AccommodationNotFound.class)
    public ResponseEntity<?> handleAccommodationNotFound(AccommodationNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<?> handleAToken(AccommodationNotFound ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", ex.getMessage()));
    }

//    @ExceptionHandler(Exception.class)
//    public Object  exceptionHandler(Exception e, HandlerMethod handler, Model model) {
//        e.printStackTrace();
//
//        boolean isRestController = handler.getBeanType().isAnnotationPresent(RestController.class);//해당 컨트롤러 클래스에 @RestController가 붙었는지를 판별
//        if (isRestController) {
//            // REST API 요청 → JSON 반환
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500
//                .body(new ApiExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
//        } else {
//            // 일반 요청 → HTML 페이지 렌더링
//            model.addAttribute("errorMessage", e.getMessage());
//            return "error-page";
//        }
//    }


    //회원가입 시 이메일 중복 처리 (409 Conflict)
    @ExceptionHandler(EmailDuplicateException.class)
    public ResponseEntity<ApiExceptionDto> handleEmailDuplicate(EmailDuplicateException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT) // 409 Conflict: 리소스 충돌 (이미 존재)
                .body(new ApiExceptionDto(HttpStatus.CONFLICT, e.getMessage()));
    }

    /**
     * 사용자 조회 실패 처리 (404 Not Found 또는 400 Bad Request)
     */
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ApiExceptionDto> handleMemberNotFound(MemberNotFoundException e) {
        // 로그인/조회 실패이므로 404 또는 400을 사용할 수 있으나, 여기서는 404를 선택
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiExceptionDto(HttpStatus.NOT_FOUND, e.getMessage()));
    }

    /**
     * 로그인 실패 처리: 비밀번호 불일치 및 사용자 미발견 시 (401 Unauthorized)
     * MemberNotFoundException 대신 InvalidCredentialsException을 처리하여 보안 강화 (사용자 존재 유무 노출 방지)
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiExceptionDto> handleInvalidCredentials(InvalidCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED) // 401 Unauthorized
                .body(new ApiExceptionDto(HttpStatus.UNAUTHORIZED, e.getMessage()));
    }

    /**
     * 계정 비활성화 상태 처리 (403 Forbidden 또는 400 Bad Request)
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiExceptionDto> handleIllegalState(IllegalStateException e) {
        // IllegalStateException은 너무 광범위하므로, 특정 비즈니스 예외(예: DisabledMemberException)를 만드는 것이 더 좋음
        if (e.getMessage().contains("비활성화된 계정입니다")) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN) // 403 Forbidden (권한은 있으나 접근 불가)
                    .body(new ApiExceptionDto(HttpStatus.FORBIDDEN, e.getMessage()));
        }
        return exceptionHandler(e);
    }

    // Spring Security 관련 예외 처리 (Optional, MemberService 로직 변경 필요)
    /*
    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<ApiExceptionDto> handleAuthFail(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED) // 401 Unauthorized
                .body(new ApiExceptionDto(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다."));
    }
    */

}