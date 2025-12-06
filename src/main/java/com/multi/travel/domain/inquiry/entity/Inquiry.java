package com.multi.travel.domain.inquiry.entity;

/*
 * Please explain the class!!!
 *
 * @filename    : Inquiry
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 2. 화요일
 */

import com.multi.travel.domain.inquiry.enums.InquiryType;
import com.multi.travel.domain.inquiry.enums.StatusType;
import com.multi.travel.domain.member.entity.Member;
import com.multi.travel.domain.place.entity.Place;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_inq")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquirer_id")
    private Member inquirer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "answer_content", columnDefinition = "TEXT")
    private String answerContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responder_id")
    private Member responder;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InquiryType inquiryType;


    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private StatusType status = StatusType.PENDING;


    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void updateAnswer(String answer, Member responder) {
        this.answerContent = answer;
        this.responder = responder;
        this.status = StatusType.COMPLETED;
    }

    public void updateStatus(StatusType status) {
        this.status = status;
    }

}
