package com.multi.travel.auth.service;

/*
 * Please explain the class!!!
 *
 * @filename    : CustomUserDetailService
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 2. 화요일
 */


import com.multi.travel.auth.user.CustomUser;
import com.multi.travel.domain.member.entity.Member;
import com.multi.travel.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email));

        return CustomUser.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .authorities(Collections.singletonList(
                        new SimpleGrantedAuthority(member.getRole())
                ))
                .name(member.getUsername())
                .status(member.getStatus())
                .build();

    }
}
