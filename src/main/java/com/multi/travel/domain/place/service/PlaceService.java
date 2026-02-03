package com.multi.travel.domain.place.service;

/*
 * Please explain the class!!!
 *
 * @filename    : PlaceService
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 5. 금요일
 */

import com.multi.travel.domain.category.entity.Category;
import com.multi.travel.domain.category.repository.CategoryRepository;
import com.multi.travel.domain.place.dto.*;
import com.multi.travel.domain.place.entity.Place;
import com.multi.travel.domain.place.enums.PlaceStatus;
import com.multi.travel.domain.place.repository.PlaceRepository;
import com.multi.travel.domain.place.img.entity.PlaceImage;
import com.multi.travel.domain.place.img.repository.PlaceImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceImageRepository placeImageRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<PlaceListResDTO> getPlaces(PlaceListReqDTO req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize(), getSort(req.getSort()));

        Page<Place> result = placeRepository.searchPlaces(
                req.getCategoryCode(),
                req.getKeyword(),
                pageable
        );

        return result.map(PlaceListResDTO::from);
    }

    @Transactional(readOnly = true)
    public PlaceDetailResDTO getPlace(Long seedId) {
        Place place = placeRepository.findById(seedId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "해당 장소를 찾을 수 없습니다. seedId=" + seedId
                ));

        return PlaceDetailResDTO.from(place);
    }

    @Transactional
    public Long createPlace(PlaceCreateReqDTO requestDTO) {

        Category category = categoryRepository.findById(requestDTO.getCategoryCode())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "해당 카테고리를 찾을 수 없습니다. categoryCode=" + requestDTO.getCategoryCode()
                ));

        Place place = requestDTO.toEntity(category);
        placeRepository.save(place);

        if (requestDTO.getImages() != null && !requestDTO.getImages().isEmpty()) {

            List<PlaceImage> images = requestDTO.getImages().stream()
                    .map(imgDTO -> imgDTO.toEntity(place))
                    .toList();

            placeImageRepository.saveAll(images);

            if (images.stream().noneMatch(PlaceImage::isMain)) {
                images.get(0).setMain(true);
            }
        }

        return place.getId();
    }

    @Transactional
    public void updatePlace(Long id, PlaceUpdateReqDTO requestDTO) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "해당 장소를 찾을 수 없습니다. id=" + id
                ));

        Category category = null;
        if (requestDTO.getCategoryCode() != null) {
            category = categoryRepository.findById(requestDTO.getCategoryCode())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "해당 카테고리를 찾을 수 없습니다. categoryCode=" + requestDTO.getCategoryCode()
                    ));
        }

        place.updateValue(requestDTO, category);
    }

    @Transactional
    public void deletePlace(Long id, PlaceStatus status) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "해당 장소를 찾을 수 없습니다. id=" + id
                ));

        place.updateActiveState(status);
    }

    @Transactional
    public void increaseViewCount(Long placeId) {
        placeRepository.incrementViewCount(placeId);
    }

    private Sort getSort(String sort) {
        if (sort == null) return Sort.by("id").descending();

        return switch (sort.toLowerCase()) {
            case "viewcount" -> Sort.by("viewCount").descending();
            case "recent" -> Sort.by("updatedAt").descending();
            default -> Sort.by("id").descending();
        };
    }

    public List<PlaceNearbyResDTO> getPlacesNearby(double lat, double lon, double dist) {
        List<Object[]> results = placeRepository.findNearbyWithDistance(lat, lon, dist);

        return results.stream()
                .map(row -> {
                    Place place = (Place) row[0];  // Place 엔티티
                    Double distance = ((Number) row[1]).doubleValue(); // 계산된 거리

                    return PlaceNearbyResDTO.from(place, distance);
                })
                .toList();
    }
}