package com.multi.travel.domain.place.dto;

/*
 * Please explain the class!!!
 *
 * @filename    : PlaceRequestDTO
 * @author      : Choi MinHyeok
 * @since       : 25. 12. 5. 금요일
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceListReqDTO {
    private Long categoryId;

    private String keyword = "";

    private String sort = "updateAt";

    private Integer page = 0;

    private Integer size = 20;
}
