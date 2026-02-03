package com.multi.travel.domain.place.service;

import com.multi.travel.domain.place.dto.PlaceListItemDto;
import com.multi.travel.domain.place.enums.PlaceStatus;
import com.multi.travel.domain.place.repository.PlaceRepository;
import com.multi.travel.domain.seed.enums.SeedStatus;
import com.multi.travel.domain.seed.repository.PlaceSeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

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

    private final PlaceRepository placeRepository;
    private final PlaceSeedRepository placeSeedRepository;

    public List<PlaceListItemDto> getUnifiedList(
            String keyword,
            Integer areaCode,
            Integer contentTypeId
    ) {

        List<PlaceListItemDto> places =
                placeRepository.searchForList(keyword, areaCode, contentTypeId, PlaceStatus.ACTIVE)
                        .stream()
                        .map(p -> PlaceListItemDto.fromPlace(p, placeholderUrl))
                        .toList();

        List<PlaceListItemDto> seeds =
                placeSeedRepository.searchForList(keyword, areaCode, contentTypeId, SeedStatus.COLLECTED)
                        .stream()
                        .map(s -> PlaceListItemDto.fromSeed(s, placeholderUrl))
                        .toList();

        return Stream.concat(places.stream(), seeds.stream())
                .sorted(Comparator.comparing(PlaceListItemDto::getTitle))
                .toList();

    }
}
