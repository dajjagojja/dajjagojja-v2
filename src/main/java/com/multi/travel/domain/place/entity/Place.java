package com.multi.travel.domain.place.entity;

/*
 * Please explain the class!!!
 *
 * @filename    : Place
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 1. 월요일
 */

import com.multi.travel.domain.category.entity.Category;
import com.multi.travel.domain.place.enums.PlaceType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_plc")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content_id", unique = true)
    private Long contentId;

    @Column(name = "place_type")
    @Enumerated(EnumType.STRING)
    private PlaceType placeType;


    @Column
    private String title;

    @Column
    private String address;

    @Column
    private String tel;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(precision = 13, scale = 10)
    private BigDecimal mapx;  // 경도

    @Column(precision = 13, scale = 10)
    private BigDecimal mapy;  // 위도

    @Column
    private String parking;

    @Column(name = "time_available")
    private String timeAvailable;

    @Column(name = "open_time")
    private String openTime;

    @Column(name = "rest_date")
    private String restDate;

    @Column(name = "best_menu")
    private String bestMenu;

    @Column(name = "check_in")
    private String checkIn;

    @Column(name = "check_out")
    private String checkOut;

    @CreatedDate
    @Column(name = "create_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "view_count")
    private int viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_code")
    private Category category;
}
