package com.multi.travel.auth.jwt.service;

import com.multi.travel.auth.jwt.domain.RefreshToken;
import com.multi.travel.auth.jwt.dto.TokenDto;
import com.multi.travel.auth.jwt.repository.RefreshTokenRepository;
import com.multi.travel.common.exception.RefreshTokenException;
import com.multi.travel.common.exception.TokenException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    //private final MemberRepository memberRepository;

    /* ===========================================================
       ✅ 1️⃣ 로그인 시 : AccessToken + RefreshToken 최초 발급
       =========================================================== */
    public TokenDto issueTokens(String email, List<String> roles) {
        // ✅ roles 전달
        String refreshToken = handleRefreshToken(email, roles);
        String accessToken  = createAccessToken(email, roles);

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /* ===========================================================
       ✅ 2️⃣ AccessToken 만료 시 : RefreshToken으로 AccessToken 재발급
       =========================================================== */
    public TokenDto refreshAccessToken(String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new TokenException("리프레시 토큰이 존재하지 않습니다.");
        }

        String pureToken = resolveToken(refreshToken);
        Claims claims = tokenProvider.parseClaims(pureToken);

        String email = claims.getSubject();
        String role = (String) claims.get("auth");

        if (role == null || role.isBlank()) {
            throw new TokenException("리프레시 토큰에 권한 정보가 없습니다.");
        }

        List<String> roles = Arrays.asList(role.split(","));

        // 새 AccessToken 재발급
        String newAccessToken = createAccessToken(email, roles);

        log.info("[TokenService] AccessToken 재발급 완료 → {}", email);

        return TokenDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken) // refresh는 그대로 유지
                .build();
    }

    /* ===========================================================
       ✅ 공용 내부 로직
       =========================================================== */

    /** ✅ RefreshToken 생성 및 관리 (JPA 기반, roles 반영) */
    @Transactional(noRollbackFor = RefreshTokenException.class)
    public String handleRefreshToken(String email, List<String> roles) {
        log.info("🔍 handleRefreshToken() 실행 중, 트랜잭션 활성 상태: {}",
                TransactionSynchronizationManager.isActualTransactionActive());

        Optional<RefreshToken> existingToken = refreshTokenRepository.findByEmail(email);
        LocalDateTime now = LocalDateTime.now();

        if (existingToken.isPresent()) {
            RefreshToken token = existingToken.get();

            // ✅ 만료 여부 확인
            if (token.getExpiredAt().isBefore(now)) {
                log.warn("[TokenService] 기존 RefreshToken 만료됨 → {}", email);
                refreshTokenRepository.deleteByEmail(email);
                throw new RefreshTokenException("Refresh token이 만료되었습니다. 다시 로그인해주세요.");
            } else {
                log.info("[TokenService] 기존 RefreshToken 재사용 → {}", email);
                return token.getRefreshToken();
            }
        }

        // ✅ 새 RefreshToken 생성 (roles 포함)
        String reToken = createRefreshToken(email, roles);

        if (tokenProvider.validateToken(reToken)) {
            RefreshToken newToken = RefreshToken.builder()
                    .email(email)
                    .refreshToken(reToken)
                    .expiredAt(tokenProvider.getRefreshTokenExpiry())
                    .issuedAt(LocalDateTime.now())
                    .build();

            refreshTokenRepository.save(newToken);
            log.info("[TokenService] 새 RefreshToken 발급 및 저장 완료 → {}", email);
        } else {
            log.error("[TokenService] RefreshToken 검증 실패 → {}", email);
            throw new RefreshTokenException("RefreshToken 생성 오류 발생");
        }

        return reToken;
    }

    /**
     * AccessToken 생성
     */
    private String createAccessToken(String email, List<String> roles) {
        return tokenProvider.generateToken(email, roles, "A");
    }

    private String createRefreshToken(String email, List<String> roles) {
        if (roles == null || roles.isEmpty()) {
            // DB에서 role을 가져와서 fallback 시키기
            //String role = memberRepository.findByEmail(email)
            //        .map(Member::getRole)
            //        .orElse("ROLE_USER");
            //roles = List.of(role);
        }
        return tokenProvider.generateToken(email, roles, "R");
    }

    /** "Bearer " 접두어 제거 */
    private String resolveToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }

    /* ===========================================================
       ✅ 로그아웃 시 RefreshToken 삭제
       =========================================================== */
    @Transactional
    public void deleteRefreshToken(String accessToken) {
        String token = resolveToken(accessToken);
        String email = tokenProvider.getEmail(token);
        refreshTokenRepository.deleteByEmail(email);
        log.info("[TokenService] 리프레시 토큰 삭제 완료 → {}", email);
    }

    /* ===========================================================
       ✅ 쿠키에서 AccessToken 추출 (프론트 통신용)
       =========================================================== */
    public String resolveTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }

        for (Cookie cookie : request.getCookies()) {
            if ("access_token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
