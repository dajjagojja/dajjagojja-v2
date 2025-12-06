package com.multi.travel.domain.place.dto;

/*
 * Please explain the class!!!
 *
 * @filename    : PlaceUpdateReqDTO
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 6. 토요일
 */


import com.multi.travel.domain.place.enums.PlaceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceUpdateReqDTO {
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
}
