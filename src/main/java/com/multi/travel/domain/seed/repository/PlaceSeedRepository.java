package com.multi.travel.domain.seed.repository;

import com.multi.travel.domain.seed.entity.PlaceSeed;
import com.multi.travel.domain.seed.enums.SeedStatus;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
