package com.multi.travel.domain.place.dto;

/*
 * Please explain the class!!!
 *
 * @filename    : PlaceCreateReqDTO
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 6. 토요일
 */

import com.multi.travel.domain.category.entity.Category;
import com.multi.travel.domain.place.entity.Place;
import com.multi.travel.domain.place.enums.PlaceType;
import com.multi.travel.domain.place.img.dto.PlaceImageReqDTO;
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
public class PlaceCreateReqDTO {
    private Long id;

    @Builder.Default
    private Long contentId = -1L;
    private PlaceType placeType;
    private String title;
    private String address;
    private String tel;
    private String description;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String parking;
    private String timeAvailable;
    private String openTime;
    private String restDate;
    private String bestMenu;
    private String checkIn;
    private String checkOut;
    private String categoryCode;
    private List<PlaceImageReqDTO> images;

    public Place toEntity(Category category) {
        return Place.builder()
                .contentId(contentId)
                .title(title)
                .address(address)
                .tel(tel)
                .description(description)
                .longitude(longitude)
                .latitude(latitude)
                .parking(parking)
                .timeAvailable(timeAvailable)
                .openTime(openTime)
                .restDate(restDate)
                .bestMenu(bestMenu)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .viewCount(0)
                .category(category)
                .build();
    }
}
