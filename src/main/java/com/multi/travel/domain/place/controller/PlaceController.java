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
import com.multi.travel.domain.place.dto.PlaceNearbyResDTO;
import com.multi.travel.domain.place.dto.PlaceUpdateReqDTO;
import com.multi.travel.domain.place.enums.PlaceStatus;
import com.multi.travel.domain.place.service.PlaceQueryService;
import com.multi.travel.domain.place.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
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

    @Operation(
            summary = "장소 목록 조회",
            description = """
                    Place + Seed 데이터를 통합하여 장소 목록을 조회합니다.
                    
                    - keyword: 장소명 기준 검색
                    - areaCode: 지역 코드 필터
                    - contentTypeId: 관광 콘텐츠 타입 필터
                    - 페이지네이션 및 정렬 지원 (page, size, sort)
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "장소 목록 조회 성공"
    )
    @GetMapping("/places")
    public ResponseEntity<ResponseDto> getPlaces(

            @Parameter(
                    description = "검색 키워드 (장소명 기준)",
                    example = "서울"
            )
            @RequestParam(required = false)
            String keyword,

            @Parameter(
                    description = "지역 코드 (예: 1=서울, 2=인천, 31=경기)",
                    example = "1"
            )
            @RequestParam(required = false)
            Integer areaCode,

            @Parameter(
                    description = "콘텐츠 타입 ID (예: 12=관광지, 32=숙박시설, 39=음식점)",
                    example = "12"
            )
            @RequestParam(required = false)
            Integer contentTypeId,

            @ParameterObject
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                new ResponseDto(
                        HttpStatus.OK,
                        "장소 목록 조회",
                        placeQueryService.getUnifiedList(
                                keyword,
                                areaCode,
                                contentTypeId,
                                pageable
                        )
                )
        );
    }


    @Operation(
            summary = "장소 상세 조회",
            description = "Place / Seed 데이터를 구분하여 단일 장소의 상세 정보를 조회합니다."
    )
    @GetMapping("/places/{contentId}")
    public ResponseEntity<ResponseDto> getPlaceDetail(
            @PathVariable Long contentId
    ) {
        return ResponseEntity.ok(
                new ResponseDto(
                        HttpStatus.OK,
                        "장소 상세 조회",
                        placeQueryService.getPlaceDetailByContentId(contentId)
                )
        );
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
