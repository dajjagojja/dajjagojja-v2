package com.multi.travel.domain.member.service;

import com.multi.travel.auth.jwt.dto.TokenDto;
import com.multi.travel.auth.jwt.service.TokenService;
import com.multi.travel.auth.user.enums.UserStatus;
import com.multi.travel.common.exception.EmailDuplicateException;
import com.multi.travel.common.exception.InvalidCredentialsException;
import com.multi.travel.domain.member.dto.LoginRequestDto;
import com.multi.travel.domain.member.dto.SignUpRequestDto;
import com.multi.travel.domain.member.entity.Member;
import com.multi.travel.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Please explain the class!!!
 *
 * @author : Kim hayeon
 * @filename : MemberService
 * @since : 2025. 12. 4. 목요일
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Transactional
    public void signUp(SignUpRequestDto requestDto) {
        log.info("[MemberService] 회원가입 시도: {}", requestDto.getEmail());

        if(memberRepository.existsByEmail(requestDto.getEmail())){
            log.warn("[MemberService] 이메일 중복 발생: {}", requestDto.getEmail());
            throw new EmailDuplicateException(requestDto.getEmail());

        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        Member member = Member.builder()
                .email(requestDto.getEmail())
                .password(encodedPassword)
                .username(requestDto.getUsername())
                .role("ROLE_USER")
                .status(UserStatus.ACTIVE)
                .userLevel(1)
                .build();

        memberRepository.save(member);
        log.info("[MemberService] 회원가입 성공 및 DB 저장 완료: {}", requestDto.getEmail());

    }


    @Transactional
    public TokenDto login(LoginRequestDto requestDto) {
        log.info("[MemberService] 로그인 시도: {}", requestDto.getEmail());

        // 1. 사용자 검증
        Member member=memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(()->{
                    log.warn("[MemberService] 로그인 실패: 존재하지 않는 이메일입니다. {}", requestDto.getEmail());
                    return new InvalidCredentialsException();
                });

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            log.warn("[MemberService] 로그인 실패: 비밀번호 불일치. {}", requestDto.getEmail());
            // 커스텀 예외 사용
            throw new InvalidCredentialsException();
        }

        // 3. 사용자 상태 검증 (ACTIVE 상태인지 확인)
        if (member.getStatus() != UserStatus.ACTIVE) {
            log.warn("[MemberService] 로그인 실패: 비활성화된 계정입니다. {}", member.getEmail());
            // 비활성화 상태에 대한 전용 예외를 만들거나, 일반 비즈니스 예외 사용
            throw new IllegalStateException("비활성화된 계정입니다. 관리자에게 문의하세요.");
        }

        // 4. 권한 목록 추출 및 토큰 발급 위임
        List<String> roles = List.of(member.getRole());
        TokenDto tokenDto = tokenService.issueTokens(member.getEmail(), roles);

        log.info("[MemberService] 로그인 성공, 토큰 발급 완료: {}", member.getEmail());
        return tokenDto;

    }


}
