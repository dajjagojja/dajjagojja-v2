package com.multi.travel.domain.place.entity;

/*
 * Please explain the class!!!
 *
 * @filename    : Place
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 1. 월요일
 */

import com.multi.travel.domain.category.entity.Category;
import com.multi.travel.domain.place.dto.PlaceUpdateReqDTO;
import com.multi.travel.domain.place.enums.PlaceDataSource;
import com.multi.travel.domain.place.enums.PlaceStatus;
import com.multi.travel.domain.place.img.entity.PlaceImage;
import com.multi.travel.domain.seed.entity.PlaceSeed;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "place")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seed_id", unique = true)
    private Long seedId;

    @Column(name = "external_content_id", unique = true)
    private Long contentId;

    @Column(name = "content_type_id", nullable = false)
    private Integer contentTypeId;

    @Column(name = "area_code", nullable = false)
    private Integer areaCode;


    @Column(nullable = false)
    private String title;

    @Column
    private String address;

    @Column
    private String tel;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(precision = 13, scale = 10)
    private BigDecimal longitude;

    @Column(precision = 13, scale = 10)
    private BigDecimal latitude;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlaceStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_source", nullable = false)
    private PlaceDataSource dataSource;

    @Column(name = "view_count")
    private int viewCount;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_code")
    private Category category;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PlaceImage> images = new ArrayList<>();

    public static Place fromSeed(PlaceSeed seed) {
        return Place.builder()
                .seedId(seed.getId())
                .contentId(seed.getContentId())
                .contentTypeId(seed.getContentTypeId())
                .areaCode(seed.getAreaCode())
                .title(seed.getTitle())
                .latitude(seed.getLatitude())
                .longitude(seed.getLongitude())
                .status(PlaceStatus.ACTIVE)
                .dataSource(PlaceDataSource.SEED_LOD)
                .build();
    }


    public void updateFromLod(PlaceUpdateReqDTO dto) {
        if (this.dataSource == PlaceDataSource.ADMIN_MANUAL) {
            return;
        }

        this.address = updateString(dto.getAddress(), this.address);
        this.tel = updateString(dto.getTel(), this.tel);
        this.description = updateString(dto.getDescription(), this.description);
        updateCommonData(dto);
    }

    public void updateByAdmin(PlaceUpdateReqDTO dto, Category category) {
        this.title = updateString(dto.getTitle(), this.title);
        this.address = updateString(dto.getAddress(), this.address);
        this.tel = updateString(dto.getTel(), this.tel);
        this.description = updateString(dto.getDescription(), this.description);
        this.longitude = updateValue(dto.getLongitude(), this.longitude);
        this.latitude = updateValue(dto.getLatitude(), this.latitude);
        updateCommonData(dto);

        if (category != null) {
            this.category = category;
        }

        this.dataSource = PlaceDataSource.ADMIN_MANUAL;
    }

    private void updateCommonData(PlaceUpdateReqDTO dto) {
        this.parking = updateString(dto.getParking(), this.parking);
        this.timeAvailable = updateString(dto.getTimeAvailable(), this.timeAvailable);
        this.openTime = updateString(dto.getOpenTime(), this.openTime);
        this.restDate = updateString(dto.getRestDate(), this.restDate);
        this.bestMenu = updateString(dto.getBestMenu(), this.bestMenu);
        this.checkIn = updateString(dto.getCheckIn(), this.checkIn);
        this.checkOut = updateString(dto.getCheckOut(), this.checkOut);
    }

    public PlaceImage getMainImage() {
        return images.stream()
                .filter(PlaceImage::isMain)
                .findFirst()
                .orElse(null);
    }

    public void increaseViewCount() {
        this.viewCount++;
    }


    private String updateString(String newValue, String oldValue) {
        return (newValue != null && !newValue.isEmpty()) ? newValue : oldValue;
    }

    public <T> T updateValue(T newValue, T oldValue) {
        return (newValue != null) ? newValue : oldValue;
    }

    public void updateActiveState(PlaceStatus status) {
        this.status = status;
    }
}