package com.multi.travel.domain.place.lod.client;

import com.multi.travel.domain.place.lod.dto.LodPlaceData;
import com.multi.travel.domain.place.lod.parser.LodPlaceParser;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : LodClient
 * @since : 26. 2. 9. 월요일
 **/


@Component
@RequiredArgsConstructor
public class LodClient {

    private static final String BASE_URL = "http://data.visitkorea.or.kr/page/";
    private static final int TIMEOUT_MS = 3_000;

    private final LodPlaceParser parser;

    public LodPlaceData fetch(Long contentId) {

        Document doc = requestHtml(contentId);
        return parser.parse(doc);
    }

    private Document requestHtml(Long contentId) {

        try {
            return Jsoup.connect(BASE_URL + contentId)
                    .userAgent("MultiTravelBot/1.0")
                    .timeout(TIMEOUT_MS)
                    .get();

        } catch (IOException e) {
            throw new IllegalStateException(
                    "LOD 페이지 조회 실패. contentId=" + contentId, e
            );
        }
    }
}