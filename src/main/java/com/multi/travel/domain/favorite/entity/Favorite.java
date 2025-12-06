package com.multi.travel.domain.favorite.entity;

/*
 * Please explain the class!!!
 *
 * @filename    : Favorite
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 2. 화요일
 */

import com.multi.travel.domain.member.entity.Member;
import com.multi.travel.domain.place.entity.Place;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "tb_fav",
        uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "place_id"})
)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(name = "is_favorite", length = 1)
    private String isFavorite;

    @CreatedDate
    private LocalDateTime createdAt;
}
