package com.multi.travel.domain.place_img.entity;

/*
 * Please explain the class!!!
 *
 * @filename    : PlaceImage
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 2. 화요일
 */

import com.multi.travel.domain.place.entity.Place;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_plc_img")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "is_main", length = 1)
    private String isMain;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}