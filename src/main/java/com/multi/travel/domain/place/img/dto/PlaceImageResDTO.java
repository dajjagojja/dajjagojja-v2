package com.multi.travel.domain.place.img.dto;

/*
 * Please explain the class!!!
 *
 * @filename    : PlaceImageResDTO
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 6. 토요일
 */


import com.multi.travel.domain.place.img.entity.PlaceImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceImageResDTO {
    private Long id;
    private String imageUrl;
    private Boolean isMain;


    public static PlaceImageResDTO from(PlaceImage placeImage) {
        return PlaceImageResDTO.builder()
                .id(placeImage.getId())
                .imageUrl(placeImage.getImageUrl())
                .isMain(placeImage.isMain())
                .build();
    }
}
