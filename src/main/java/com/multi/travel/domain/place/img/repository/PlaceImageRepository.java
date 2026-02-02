package com.multi.travel.domain.place.img.repository;

/*
 * Please explain the class!!!
 *
 * @filename    : PlaceImageRepository
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 6. 토요일
 */


import com.multi.travel.domain.place.img.entity.PlaceImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceImageRepository extends JpaRepository<PlaceImage, Long> {
}
