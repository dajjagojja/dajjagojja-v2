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
import com.multi.travel.domain.place.enums.PlaceStatus;
import com.multi.travel.domain.place.enums.PlaceType;
import com.multi.travel.domain.place.img.entity.PlaceImage;
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
@Table(name = "tb_plc")
@Getter
@Setter
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
    private BigDecimal longitude;  // 경도

    @Column(precision = 13, scale = 10)
    private BigDecimal latitude;  // 위도

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
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "view_count")
    private int viewCount;

    @Enumerated(EnumType.STRING)
    private PlaceStatus status;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_code")
    private Category category;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PlaceImage> images = new ArrayList<>();

    public PlaceImage getMainImage() {
        return images.stream()
                .filter(PlaceImage::isMain)
                .findFirst()
                .orElse(null);
    }

    public void updateValue(PlaceUpdateReqDTO requestDTO, Category category) {

        this.title = updateString(requestDTO.getTitle(), this.title);
        this.address = updateString(requestDTO.getAddress(), this.address);
        this.tel = updateString(requestDTO.getTel(), this.tel);
        this.description = updateString(requestDTO.getDescription(), this.description);

        this.longitude = updateValue(requestDTO.getLongitude(), this.longitude);
        this.latitude = updateValue(requestDTO.getLatitude(), this.latitude);

        this.parking = updateString(requestDTO.getParking(), this.parking);
        this.timeAvailable = updateString(requestDTO.getTimeAvailable(), this.timeAvailable);
        this.openTime = updateString(requestDTO.getOpenTime(), this.openTime);
        this.restDate = updateString(requestDTO.getRestDate(), this.restDate);
        this.bestMenu = updateString(requestDTO.getBestMenu(), this.bestMenu);
        this.checkIn = updateString(requestDTO.getCheckIn(), this.checkIn);
        this.checkOut = updateString(requestDTO.getCheckOut(), this.checkOut);

        if (category != null) {
            this.category = category;
        }
    }

    private String updateString(String newValue, String oldValue) {
        return (newValue != null && !newValue.isEmpty()) ? newValue : oldValue;
    }

    private <T> T updateValue(T newValue, T oldValue) {
        return (newValue != null) ? newValue : oldValue;
    }

    public void updateActiveState(PlaceStatus newStatus) {
        this.status = newStatus;
    }

}
