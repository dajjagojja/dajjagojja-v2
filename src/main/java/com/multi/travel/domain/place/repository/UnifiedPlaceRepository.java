package com.multi.travel.domain.place.repository;

import com.multi.travel.domain.place.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : UnifiedPlaceRepository
 * @since : 26. 2. 9. 월요일
 **/


public interface UnifiedPlaceRepository extends Repository<Place, Long> {

    @Query(
            value = """
            SELECT 
                p.id                    AS placeId,
                p.external_content_id   AS contentId,
                p.title,
                p.area_code             AS areaCode,
                p.content_type_id       AS contentTypeId,
                'PLACE'                 AS source
            FROM place p
            WHERE p.status = 'ACTIVE'
              AND (:keyword IS NULL OR p.title LIKE CONCAT('%', :keyword, '%'))
              AND (:areaCode IS NULL OR p.area_code = :areaCode)
              AND (:contentTypeId IS NULL OR p.content_type_id = :contentTypeId)

            UNION ALL

            SELECT
                NULL                    AS placeId,
                s.content_id            AS contentId,
                s.title,
                s.area_code             AS areaCode,
                s.content_type_id       AS contentTypeId,
                'SEED'                  AS source
            FROM place_seed s
            WHERE s.status = 'COLLECTED'
              AND (:keyword IS NULL OR s.title LIKE CONCAT('%', :keyword, '%'))
              AND (:areaCode IS NULL OR s.area_code = :areaCode)
              AND (:contentTypeId IS NULL OR s.content_type_id = :contentTypeId)

            ORDER BY title
        """,
            countQuery = """
            SELECT COUNT(*) FROM (
                SELECT p.id
                FROM place p
                WHERE p.status = 'ACTIVE'
                  AND (:keyword IS NULL OR p.title LIKE CONCAT('%', :keyword, '%'))
                  AND (:areaCode IS NULL OR p.area_code = :areaCode)
                  AND (:contentTypeId IS NULL OR p.content_type_id = :contentTypeId)

                UNION ALL

                SELECT s.id
                FROM place_seed s
                WHERE s.status = 'COLLECTED'
                  AND (:keyword IS NULL OR s.title LIKE CONCAT('%', :keyword, '%'))
                  AND (:areaCode IS NULL OR s.area_code = :areaCode)
                  AND (:contentTypeId IS NULL OR s.content_type_id = :contentTypeId)
            ) t
        """,
            nativeQuery = true
    )
    Page<UnifiedPlaceView> searchUnified(
            String keyword,
            Integer areaCode,
            Integer contentTypeId,
            Pageable pageable
    );
}