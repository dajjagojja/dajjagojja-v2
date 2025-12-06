package com.multi.travel.domain.place.dto;

/*
 * Please explain the class!!!
 *
 * @filename    : PlaceNearbyResDTO
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 6. 토요일
 */


import com.multi.travel.domain.place.entity.Place;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PlaceNearbyResDTO {
    private Long id;
    private String title;
    private double latitude;
    private double longitude;
    private String mainImage;
    private double distance;


    public static PlaceNearbyResDTO from(Place place, Double distance) {
        return PlaceNearbyResDTO.builder()
                .id(place.getId())
                .title(place.getTitle())
                .mainImage(place.getMainImage().getImageUrl())
                .latitude(place.getLatitude().doubleValue())
                .longitude(place.getLongitude().doubleValue())
                .distance(distance)
                .build();
    }
}
