package com.multi.travel.domain.seed.entity;

import com.multi.travel.domain.seed.enums.SeedStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : PlaceSeed
 * @since : 26. 2. 2. 월요일
 **/


@Entity
@Table(
        name = "place_seed",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_place_seed_content_id", columnNames = "content_id")
        },
        indexes = {
                @Index(name = "idx_place_seed_area", columnList = "area_code"),
                @Index(name = "idx_place_seed_location", columnList = "latitude, longitude"),
                @Index(name = "idx_place_seed_type", columnList = "content_type_id"),
                @Index(name = "idx_place_seed_status", columnList = "status")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceSeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content_id", nullable = false, updatable = false)
    private Long contentId;

    @Column(name = "content_type_id", nullable = false)
    private Integer contentTypeId;

    @Column(name = "area_code", nullable = false)
    private Integer areaCode;

    @Column(name = "sigungu_code")
    private Integer sigunguCode;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(precision = 10, scale = 7)
    private Double latitude;

    @Column(precision = 10, scale = 7)
    private Double longitude;

    @Column(length = 500)
    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SeedStatus status;

    @Column(name = "source_modified_at")
    private LocalDateTime sourceModifiedAt;

    @Column(nullable = false)
    private LocalDateTime collectedAt;

    @Column(length = 255)
    private String failReason;

    @Builder
    public PlaceSeed(
            Long contentId,
            Integer contentTypeId,
            Integer areaCode,
            Integer sigunguCode,
            String title,
            Double latitude,
            Double longitude,
            String thumbnailUrl,
            LocalDateTime sourceModifiedAt,
            SeedStatus status,
            LocalDateTime collectedAt,
            String failReason
    ) {
        this.contentId = contentId;
        this.contentTypeId = contentTypeId;
        this.areaCode = areaCode;
        this.sigunguCode = sigunguCode;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.thumbnailUrl = thumbnailUrl;
        this.sourceModifiedAt = sourceModifiedAt;
        this.status = status;
        this.collectedAt = collectedAt;
        this.failReason = failReason;
    }

    public void markConverted() {
        this.status = SeedStatus.CONVERTED;
    }

    public void markFailed(String reason) {
        this.status = SeedStatus.FAILED;
        this.failReason = reason;
    }
}