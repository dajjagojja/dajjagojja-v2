package com.multi.travel.auth.controller;

import com.multi.travel.auth.jwt.dto.TokenDto;
import com.multi.travel.common.ResponseDto;
import com.multi.travel.domain.member.dto.LoginRequestDto;
import com.multi.travel.domain.member.dto.SignUpRequestDto;
import com.multi.travel.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : AutuController
 * @since : 26. 2. 2. 월요일
 **/


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody SignUpRequestDto requestDto) {
        memberService.signUp(requestDto);

        return ok(new ResponseDto(HttpStatus.CREATED, "회원가입 성공", null));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto requestDto) {
        TokenDto tokenDto = memberService.login(requestDto);

        return ResponseEntity.ok(tokenDto);
    }
}
