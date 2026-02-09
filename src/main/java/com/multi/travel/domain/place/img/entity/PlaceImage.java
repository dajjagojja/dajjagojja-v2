package com.multi.travel.domain.place.img.entity;

/*
 * Please explain the class!!!
 *
 * @filename    : PlaceImage
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 2. 화요일
 */

import com.multi.travel.domain.place.entity.Place;
import com.multi.travel.domain.place.img.PlaceImageSource;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "place_image")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "is_main", nullable = false)
    private boolean isMain;

    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false)
    private PlaceImageSource source;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public void setMain(boolean tf) {
        this.isMain = tf;
    }
}
