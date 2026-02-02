package com.multi.travel.domain.seed.batch;

import com.multi.travel.common.exception.PlaceSeedApiException;
import com.multi.travel.domain.seed.dto.AreaBasedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : PlaceSeedApiClient
 * @since : 26. 2. 2. 월요일
 **/


@Component
@RequiredArgsConstructor
public class PlaceSeedApiClient {

    private final RestTemplate restTemplate;

    @Value("${tour-api.service-key}")
    private String serviceKey;

    public AreaBasedResponse fetchAreaBasedList(
            int areaCode,
            int contentTypeId,
            int pageNo,
            int numOfRows
    ) {
        String url = UriComponentsBuilder
                .fromHttpUrl("https://apis.data.go.kr/B551011/KorService2/areaBasedList2")
                .queryParam("serviceKey", serviceKey)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "travel")
                .queryParam("_type", "json")
                .queryParam("areaCode", areaCode)
                .queryParam("contentTypeId", contentTypeId)
                .queryParam("pageNo", pageNo)
                .queryParam("numOfRows", numOfRows)
                .build(false)
                .toUriString();

        try {
            return restTemplate.getForObject(url, AreaBasedResponse.class);
        } catch (Exception e) {
            throw new PlaceSeedApiException(e);
        }
    }
}
