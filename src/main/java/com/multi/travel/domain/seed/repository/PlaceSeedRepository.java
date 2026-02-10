package com.multi.travel.domain.seed.repository;

import com.multi.travel.domain.seed.entity.PlaceSeed;
import com.multi.travel.domain.seed.enums.SeedStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : PlaceSeedRepository
 * @since : 26. 2. 2. 월요일
 **/


public interface PlaceSeedRepository extends JpaRepository<PlaceSeed, Long> {
    boolean existsByContentId(Long contentId);

    boolean existsByStatus(SeedStatus seedStatus);

    @Query("""
        select ps
        from PlaceSeed ps
        where ps.status <> :failed
          and (:keyword = '' or ps.title like concat('%', :keyword, '%'))
          and (:areaCode is null or ps.areaCode = :areaCode)
          and (:contentTypeId is null or ps.contentTypeId = :contentTypeId)
        """)
    Page<PlaceSeed> search(
            @Param("keyword") String keyword,
            @Param("areaCode") Integer areaCode,
            @Param("contentTypeId") Integer contentTypeId,
            @Param("failed") SeedStatus failed,
            Pageable pageable
    );

    @Query("""

            select ps
        from PlaceSeed ps
        where ps.status = :collected
          and (:keyword = '' or ps.title like concat('%', :keyword, '%'))
          and (:areaCode is null or ps.areaCode = :areaCode)
          and (:contentTypeId is null or ps.contentTypeId = :contentTypeId)
        """)
    List<PlaceSeed> searchForList(
            @Param("keyword") String keyword,
            @Param("areaCode") Integer areaCode,
            @Param("contentTypeId") Integer contentTypeId,
            @Param("collected") SeedStatus collected
    );

    Optional<PlaceSeed> findByContentId(Long contentId);
}
