package com.multi.travel.auth.service;

/*
 * Please explain the class!!!
 *
 * @filename    : CustomUserDetailService
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 2. 화요일
 */


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService {
    //private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        //Member member = memberRepository.findByLoginId(loginId)
        //        .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + loginId));
        //
        //return CustomUser.builder()
        //        .email(member.getEmail())
        //        .password(member.getPassword())
        //        .authorities(Collections.singletonList(
        //                new SimpleGrantedAuthority(member.getRole())
        //        ))
        //        .build();
        return null;
    }
}
