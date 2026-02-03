package com.multi.travel.domain.seed.service;

import com.multi.travel.domain.seed.dto.PlaceSeedListResDto;
import com.multi.travel.domain.seed.entity.PlaceSeed;
import com.multi.travel.domain.seed.enums.SeedStatus;
import com.multi.travel.domain.seed.repository.PlaceSeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : PlaceSeedService
 * @since : 26. 2. 2. 월요일
 **/

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceSeedService {

    @Value("${app.place.placeholder-url}")
    private String placeholderUrl;

    private final PlaceSeedRepository placeSeedRepository;

    public Page<PlaceSeedListResDto> getSeeds(
            String keyword,
            Integer areaCode,
            Integer contentTypeId,
            Pageable pageable
    ) {
        Page<PlaceSeed> seeds =
                placeSeedRepository.search(
                        keyword != null ? keyword : "",
                        areaCode,
                        contentTypeId,
                        SeedStatus.FAILED,
                        pageable
                );

        return seeds.map(seed ->
                PlaceSeedListResDto.from(seed, placeholderUrl)
        );
    }

}
