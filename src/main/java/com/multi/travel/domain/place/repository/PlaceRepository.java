package com.multi.travel.domain.place.repository;

/*
 * Please explain the class!!!
 *
 * @filename    : PlaceRepository
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 2. 화요일
 */


import com.multi.travel.domain.place.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Modifying
    @Query("UPDATE Place p SET p.viewCount = p.viewCount + 1 WHERE p.id = :id")
    void incrementViewCount(@Param("id") Long id);


    @Query("""
            SELECT p
    FROM Place p
    WHERE (:categoryCode IS NULL OR p.category.categoryCode = :categoryCode)
      AND (:keyword IS NULL OR p.title LIKE %:keyword%)
    """)
    Page<Place> searchPlaces(
            @Param("categoryCode") String categoryCode,
            @Param("keyword") String keyword,
            Pageable pageable
    );



    @Query(value = """
    SELECT p.*,
        (6371 * acos(
            cos(radians(:lat)) *
            cos(radians(p.latitude)) *
            cos(radians(p.longitude) - radians(:lon)) +
            sin(radians(:lat)) *
            sin(radians(p.latitude))
        )) AS distance
    FROM tb_place p
    HAVING distance <= :distance
    ORDER BY distance ASC
    """, nativeQuery = true)
    List<Object[]> findNearbyWithDistance(
            @Param("lat") double lat,
            @Param("lon") double lon,
            @Param("distance") double distance
    );


}
