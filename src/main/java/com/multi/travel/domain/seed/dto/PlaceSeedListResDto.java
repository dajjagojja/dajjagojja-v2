package com.multi.travel.domain.seed.dto;

import com.multi.travel.domain.seed.entity.PlaceSeed;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : PlaceSeedResDto
 * @since : 26. 2. 3. 화요일
 **/


@Getter
@AllArgsConstructor
public class PlaceSeedListResDto {

    private Long seedId;
    private String title;
    private Integer contentTypeId;
    private Integer areaCode;
    private Integer sigunguCode;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String thumbnailUrl;

    public static PlaceSeedListResDto from(
            PlaceSeed seed,
            String placeholderUrl
    ) {
        return new PlaceSeedListResDto(
                seed.getId(),
                seed.getTitle(),
                seed.getContentTypeId(),
                seed.getAreaCode(),
                seed.getSigunguCode(),
                seed.getLatitude(),
                seed.getLongitude(),
                seed.getThumbnailUrl() != null
                        ? seed.getThumbnailUrl()
                        : placeholderUrl
        );
    }
}