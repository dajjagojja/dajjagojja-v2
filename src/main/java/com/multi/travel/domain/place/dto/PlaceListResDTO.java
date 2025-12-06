package com.multi.travel.domain.place.dto;

/*
 * Please explain the class!!!
 *
 * @filename    : PlaceListResDTO
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 5. 금요일
 */

import com.multi.travel.domain.place.entity.Place;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PlaceListResDTO {
    private Long id;
    private String title;
    private String address;
    private String mainImage;
    private int viewCount;


    public static PlaceListResDTO from(Place place) {
        return PlaceListResDTO.builder()
                .id(place.getId())
                .title(place.getTitle())
                .address(place.getAddress())
                .mainImage(place.getMainImage() != null ? place.getMainImage().getImageUrl() : null)
                //.rating(place.getRating())
                .viewCount(place.getViewCount())
                .build();
    }
}
