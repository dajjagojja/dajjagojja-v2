package com.multi.travel.domain.place.dto;

import com.multi.travel.domain.place.entity.Place;
import com.multi.travel.domain.place.img.entity.PlaceImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : PlaceDetailResDto
 * @since : 26. 2. 9. 월요일
 **/


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceDetailResDto {

    private Long placeId;
    private Long contentId;

    private String title;
    private Integer areaCode;
    private Integer contentTypeId;
    private String source;

    private String address;
    private String tel;
    private BigDecimal latitude;
    private BigDecimal longitude;

    private String description;

    private String parking;
    private String timeAvailable;
    private String openTime;
    private String restDate;
    private String bestMenu;
    private String checkIn;
    private String checkOut;

    private String categoryName;
    private int viewCount;

    private String mainImageUrl;
    private List<String> imageUrls;

    public static PlaceDetailResDto fromPlace(Place place) {
        return PlaceDetailResDto.builder()
                .placeId(place.getId())
                .contentId(place.getContentId())

                .title(place.getTitle())
                .areaCode(place.getAreaCode())
                .contentTypeId(place.getContentTypeId())
                .source(place.getDataSource().name())

                .address(place.getAddress())
                .tel(place.getTel())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .description(place.getDescription())

                .parking(place.getParking())
                .timeAvailable(place.getTimeAvailable())
                .openTime(place.getOpenTime())
                .restDate(place.getRestDate())
                .bestMenu(place.getBestMenu())
                .checkIn(place.getCheckIn())
                .checkOut(place.getCheckOut())

                .categoryName(
                        place.getCategory() != null
                                ? place.getCategory().getCategoryName()
                                : null
                )
                .viewCount(place.getViewCount())

                .mainImageUrl(
                        place.getMainImage() != null
                                ? place.getMainImage().getImageUrl()
                                : null
                )
                .imageUrls(
                        place.getImages().stream()
                                .map(PlaceImage::getImageUrl)
                                .toList()
                )
                .build();
    }
}
