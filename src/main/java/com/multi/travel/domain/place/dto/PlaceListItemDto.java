package com.multi.travel.domain.place.dto;

import com.multi.travel.domain.place.entity.Place;
import com.multi.travel.domain.place.enums.PlaceItemType;
import com.multi.travel.domain.seed.entity.PlaceSeed;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : PlaceListItemDto
 * @since : 26. 2. 3. 화요일
 **/


@Getter
@AllArgsConstructor
public class PlaceListItemDto {

    private Long id;
    private PlaceItemType type; // PLACE / SEED
    private String title;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String thumbnailUrl;

    public static PlaceListItemDto fromPlace(
            Place place,
            String placeholderUrl
    ) {
        return new PlaceListItemDto(
                place.getId(),
                PlaceItemType.PLACE,
                place.getTitle(),
                place.getLatitude(),
                place.getLongitude(),
                place.getMainImage() != null
                        ? place.getMainImage().getImageUrl()
                        : placeholderUrl
        );
    }

    public static PlaceListItemDto fromSeed(
            PlaceSeed seed,
            String placeholderUrl
    ) {
        return new PlaceListItemDto(
                seed.getId(),
                PlaceItemType.SEED,
                seed.getTitle(),
                seed.getLatitude(),
                seed.getLongitude(),
                seed.getThumbnailUrl() != null
                        ? seed.getThumbnailUrl()
                        : placeholderUrl
        );
    }
}
