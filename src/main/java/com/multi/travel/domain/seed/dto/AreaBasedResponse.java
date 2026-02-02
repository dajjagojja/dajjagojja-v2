package com.multi.travel.domain.seed.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : AreaBasedResponse
 * @since : 26. 2. 2. 월요일
 **/


@Getter
@NoArgsConstructor
public class AreaBasedResponse {

    private Response response;

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Body body;
    }

    @Getter
    @NoArgsConstructor
    public static class Body {
        private Items items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;
    }

    @Getter
    @NoArgsConstructor
    public static class Items {
        private List<AreaItem> item;
    }


    public List<AreaItem> getItems() {
        if (response == null
                || response.body == null
                || response.body.items == null
                || response.body.items.item == null) {
            return List.of();
        }
        return response.body.items.item;
    }

    public int getTotalCount() {
        return response.body.totalCount;
    }
}
