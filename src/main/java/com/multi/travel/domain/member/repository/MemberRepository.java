package com.multi.travel.domain.member.repository;

import com.multi.travel.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Please explain the class!!!
 *
 * @author : Kim hayeon
 * @filename : MemberRepository
 * @since : 2025. 12. 4. 목요일
 */

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);

}
