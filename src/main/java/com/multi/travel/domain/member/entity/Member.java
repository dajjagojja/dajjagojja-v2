package com.multi.travel.domain.member.entity;

import com.multi.travel.auth.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Please explain the class!!!
 *
 * @author : Kim hayeon
 * @filename : Member
 * @since : 2025. 12. 4. 목요일
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="tb_usr")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 50)
    private String role;


    // if (member.getStatus() == UserStatus.ACTIVE): 활성상태 확인
    // => 의미가 명확한 객체 비교를 사용하여 가독성이 높다고 함
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserStatus status;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "user_level", nullable = false)
    private Integer userLevel;


    public void updateDetails(String username, UserStatus status, Integer userLevel) {
        this.username = username;
        this.status = status;
        this.userLevel = userLevel;
    }
}
