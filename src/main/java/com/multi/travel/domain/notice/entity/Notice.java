package com.multi.travel.domain.notice.entity;

/*
 * Please explain the class!!!
 *
 * @filename    : Notice
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 2. 화요일
 */

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_ntc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @CreatedDate
    @Column(name = "create_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;


    @Column(name = "author_name")
    private String authorName;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "member_id")
    //private Member member;
}
