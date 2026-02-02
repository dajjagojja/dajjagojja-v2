package com.multi.travel.domain.seed.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : AreaItem
 * @since : 26. 2. 2. 월요일
 **/


@Getter
@NoArgsConstructor
public class AreaItem {

    @JsonProperty("contentid")
    private String contentId;

    @JsonProperty("contenttypeid")
    private Integer contentTypeId;

    @JsonProperty("areacode")
    private Integer areaCode;

    @JsonProperty("sigungucode")
    private Integer sigunguCode;

    private String title;

    @JsonProperty("mapy")
    private Double mapY;   // latitude

    @JsonProperty("mapx")
    private Double mapX;   // longitude

    @JsonProperty("firstimage")
    private String firstImage;

    @JsonProperty("modifiedtime")
    private String modifiedTime;
}
