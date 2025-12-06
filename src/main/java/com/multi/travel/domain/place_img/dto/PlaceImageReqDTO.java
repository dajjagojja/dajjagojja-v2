package com.multi.travel.domain.place_img.dto;

/*
 * Please explain the class!!!
 *
 * @filename    : PlaceImageReqDTO
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 6. 토요일
 */


import com.multi.travel.domain.place.entity.Place;
import com.multi.travel.domain.place_img.entity.PlaceImage;

public class PlaceImageReqDTO {
    private String imageUrl;
    private boolean isMain;

    public PlaceImage toEntity(Place place) {
        return PlaceImage.builder()
                .place(place)
                .imageUrl(imageUrl)
                .isMain(isMain)
                .build();
    }
}
