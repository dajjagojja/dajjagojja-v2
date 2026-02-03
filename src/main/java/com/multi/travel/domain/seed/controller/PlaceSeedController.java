package com.multi.travel.domain.seed.controller;

import com.multi.travel.common.ResponseDto;
import com.multi.travel.domain.seed.service.PlaceSeedService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : PlaceSeedController
 * @since : 26. 2. 3. 화요일
 **/

@RestController
@RequestMapping("/api/place/seeds")
@RequiredArgsConstructor
public class PlaceSeedController {

    private final PlaceSeedService placeSeedService;

    @GetMapping
    public ResponseEntity<ResponseDto> getSeeds(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false) Integer areaCode,
            @RequestParam(required = false) Integer contentTypeId,
            @PageableDefault(size = 20)
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(
                new ResponseDto(
                        HttpStatus.OK,
                        "Seed 목록 조회 성공",
                        placeSeedService.getSeeds(keyword, areaCode, contentTypeId, pageable)
                )
        );
    }

}
