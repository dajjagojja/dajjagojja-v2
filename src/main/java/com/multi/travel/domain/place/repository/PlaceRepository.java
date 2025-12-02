package com.multi.travel.domain.place.repository;

/*
 * Please explain the class!!!
 *
 * @filename    : PlaceRepository
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 2. 화요일
 */


import com.multi.travel.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Modifying
    @Query("UPDATE Place p SET p.viewCount = p.viewCount + 1 WHERE p.id = :id")
    void incrementViewCount(@Param("id") Long id);


}
