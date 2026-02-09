package com.multi.travel.domain.place.lod.dto;

import com.multi.travel.domain.place.dto.PlaceUpdateReqDTO;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : LodPlaceData
 * @since : 26. 2. 9. 월요일
 **/



@Getter
@Builder
public class LodPlaceData {

    private String title;
    private String address;
    private String tel;
    private String description;

    private BigDecimal latitude;
    private BigDecimal longitude;

    private String parking;
    private String openTime;
    private String restDate;
    private String bestMenu;
    private String timeAvailable;
    private String checkIn;
    private String checkOut;

    private List<String> imageUrls;

    public PlaceUpdateReqDTO toUpdateDto() {
        return PlaceUpdateReqDTO.builder()
                .title(title)
                .address(address)
                .tel(tel)
                .description(description)
                .latitude(latitude)
                .longitude(longitude)
                .parking(parking)
                .openTime(openTime)
                .restDate(restDate)
                .bestMenu(bestMenu)
                .timeAvailable(timeAvailable)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .build();
    }
}
