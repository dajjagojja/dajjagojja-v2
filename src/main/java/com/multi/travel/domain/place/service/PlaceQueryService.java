package com.multi.travel.domain.place.service;

import com.multi.travel.common.exception.NotFoundException;
import com.multi.travel.domain.place.dto.PlaceDetailResDto;
import com.multi.travel.domain.place.dto.PlaceListItemDto;
import com.multi.travel.domain.place.entity.Place;
import com.multi.travel.domain.place.lod.client.LodClient;
import com.multi.travel.domain.place.repository.PlaceRepository;
import com.multi.travel.domain.place.repository.UnifiedPlaceRepository;
import com.multi.travel.domain.seed.entity.PlaceSeed;
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
 * @filename : PlaceQueryService
 * @since : 26. 2. 3. 화요일
 **/


@Service
@RequiredArgsConstructor
public class PlaceQueryService {

    @Value("${app.place.placeholder-url}")
    private String placeholderUrl;

    private final UnifiedPlaceRepository unifiedPlaceRepository;
    private final PlaceRepository placeRepository;
    private final PlaceSeedRepository placeSeedRepository;
    private final PlacePromotionService placePromotionService;

    private final LodClient lodClient;

    public Page<PlaceListItemDto> getUnifiedList(
            String keyword,
            Integer areaCode,
            Integer contentTypeId,
            Pageable pageable
    ) {

        return unifiedPlaceRepository
                .searchUnified(keyword, areaCode, contentTypeId, pageable)
                .map(v -> PlaceListItemDto.fromUnified(v, placeholderUrl));
    }


    @Transactional
    public PlaceDetailResDto getPlaceDetailByContentId(Long contentId) {

        Place place = placeRepository.findByContentId(contentId)
                .orElseGet(() -> {

                    PlaceSeed seed = placeSeedRepository.findByContentId(contentId)
                            .orElseThrow(() ->
                                    new NotFoundException(
                                            "장소를 찾을 수 없습니다. contentId=" + contentId
                                    )
                            );

                    return placePromotionService.promoteFromSeed(seed);
                });

        place.increaseViewCount();
        return PlaceDetailResDto.fromPlace(place);
    }

}
