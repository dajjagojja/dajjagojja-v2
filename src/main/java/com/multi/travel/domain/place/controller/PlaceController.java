package com.multi.travel.domain.place.controller;

/*
 * Please explain the class!!!
 *
 * @filename    : PlaceController
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 4. 목요일
 */

import com.multi.travel.common.ResponseDto;
import com.multi.travel.domain.place.dto.PlaceCreateReqDTO;
import com.multi.travel.domain.place.dto.PlaceDetailResDTO;
import com.multi.travel.domain.place.dto.PlaceNearbyResDTO;
import com.multi.travel.domain.place.dto.PlaceUpdateReqDTO;
import com.multi.travel.domain.place.enums.PlaceStatus;
import com.multi.travel.domain.place.service.PlaceQueryService;
import com.multi.travel.domain.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;
    private final PlaceQueryService placeQueryService;

    @GetMapping("/places")
    public ResponseEntity<ResponseDto> getPlaces(
            @RequestParam(required = false) String keyword
    ) {
        return ResponseEntity.ok(
                new ResponseDto(
                        HttpStatus.OK,
                        "장소 목록 조회",
                        placeQueryService.getUnifiedList(keyword, null, null)
                )
        );
    }


    @GetMapping("/places/{seedId}")
    public ResponseEntity<ResponseDto> getPlaceById(@PathVariable Long seedId) {
        PlaceDetailResDTO response = placeService.getPlace(seedId);
        placeService.increaseViewCount(seedId);
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "장소 상세 조회 성공", response));
    }

    @GetMapping("/places/nearby")
    public ResponseEntity<ResponseDto> getPlacesNearby(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam double distance
    )
    {
        List<PlaceNearbyResDTO> placesNearby = placeService.getPlacesNearby(latitude, longitude, distance);

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "주변 장소 목록 조회 성공",  placesNearby));
    }

    @PostMapping("/admin/places")
    public ResponseEntity<ResponseDto> createPlace(@RequestBody PlaceCreateReqDTO requestDTO) {
        Long createdId = placeService.createPlace(requestDTO);

        return ResponseEntity.ok(new ResponseDto(HttpStatus.CREATED, "장소 생성 성공", createdId));
    }

    @PutMapping("/admin/places/{id}")
    public ResponseEntity<ResponseDto> updatePlace(@PathVariable Long id, @RequestBody PlaceUpdateReqDTO requestDTO) {
        placeService.updatePlace(id, requestDTO);

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "장소 수정 성공", id));
    }

    @PatchMapping("/admin/places/{id}/status")
    public ResponseEntity<ResponseDto> deletePlace(@PathVariable Long id, @RequestParam PlaceStatus status) {
        placeService.deletePlace(id, status);

        Map<String, Object> result = new HashMap<>();
        result.put("status", status);

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "장소 상태 변경됨", result));
    }
}
