package com.multi.travel.domain.place.dto;

/*
 * Please explain the class!!!
 *
 * @filename    : PlaceDetailResDTO
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 6. 토요일
 */


import com.multi.travel.domain.place.entity.Place;
import com.multi.travel.domain.place_img.dto.PlaceImageResDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceDetailResDTO {
    private Long id;
    private String title;
    private String address;
    private String description;
    private BigDecimal longitude;  // 경도
    private BigDecimal latitude;  // 위도
    private String categoryName;
    private Double rating;
    private List<PlaceImageResDTO> images;
    //private int reviewCount;
    //private int favoriteCount;


    public static PlaceDetailResDTO from(Place place) {
        return PlaceDetailResDTO.builder()
                .id(place.getId())
                .title(place.getTitle())
                .address(place.getAddress())
                .description(place.getDescription())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .categoryName(place.getCategory().getCategoryName())
                .images(place.getImages().stream().map(PlaceImageResDTO::from).toList())
                .build();
    }
}
