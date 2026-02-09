package com.multi.travel.domain.place.service;

import com.multi.travel.domain.place.dto.PlaceListItemDto;
import com.multi.travel.domain.place.repository.UnifiedPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
