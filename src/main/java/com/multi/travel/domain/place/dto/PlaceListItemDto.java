package com.multi.travel.domain.place.dto;

import com.multi.travel.domain.place.enums.PlaceItemType;
import com.multi.travel.domain.place.repository.UnifiedPlaceView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : PlaceListItemDto
 * @since : 26. 2. 3. 화요일
 **/


@Getter
@AllArgsConstructor
@Builder
public class PlaceListItemDto {

    private Long id;
    private String title;
    private Integer areaCode;
    private Integer contentTypeId;
    private String source;
    private String imageUrl;

    public static PlaceListItemDto fromUnified(
            UnifiedPlaceView view,
            String placeholderUrl
    ) {
        return PlaceListItemDto.builder()
                .id(view.getId())
                .title(view.getTitle())
                .areaCode(view.getAreaCode())
                .contentTypeId(view.getContentTypeId())
                .imageUrl(placeholderUrl)
                .source(view.getSource()) // PLACE / SEED
                .build();
    }
}
