package com.multi.travel.domain.place.lod.parser;

import com.multi.travel.domain.place.lod.dto.LodPlaceData;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : LodPlaceParser
 * @since : 26. 2. 9. 월요일
 **/


@Component
public class LodPlaceParser {

    // === Property Keys ===
    private static final String K_ADDRESS = "ktop:address";
    private static final String K_TEL = "ktop:tel";
    private static final String K_PARKING = "ktop:parking";

    private static final String K_TIME_AVAILABLE = "ktop:timeAvailable";
    private static final String K_USE_TIME = "ktop:useTime";
    private static final String K_OPEN_TIME = "ktop:openTime";

    private static final String K_REST_DATE = "ktop:restDate";
    private static final String K_CLOSED_DAY = "ktop:closedDay";

    private static final String K_BEST_MENU = "ktop:bestMenu";
    private static final String K_CHECK_IN = "ktop:checkInTime";
    private static final String K_CHECK_OUT = "ktop:checkOutTime";

    private static final String K_LAT = "geo-pos:lat";
    private static final String K_LNG = "geo-pos:long";

    public LodPlaceData parse(Document doc) {

        Map<String, String> props = extractPropertyMap(doc);

        return LodPlaceData.builder()
                // === 기본 정보 ===
                .title(extractTitle(doc))
                .description(extractDescription(doc))
                .address(props.get(K_ADDRESS))
                .tel(props.get(K_TEL))

                // === 이용 정보 ===
                .parking(props.get(K_PARKING))
                .timeAvailable(
                        firstNonNull(
                                props.get(K_TIME_AVAILABLE),
                                props.get(K_USE_TIME),
                                props.get(K_OPEN_TIME)
                        )
                )
                .openTime(props.get(K_OPEN_TIME))
                .restDate(
                        firstNonNull(
                                props.get(K_REST_DATE),
                                props.get(K_CLOSED_DAY)
                        )
                )
                .bestMenu(props.get(K_BEST_MENU))
                .checkIn(props.get(K_CHECK_IN))
                .checkOut(props.get(K_CHECK_OUT))

                // === 위치 ===
                .latitude(parseDecimal(props.get(K_LAT)))
                .longitude(parseDecimal(props.get(K_LNG)))

                // === 이미지 ===
                .imageUrls(extractImageUrls(doc))
                .build();
    }

    /**
     * Property 테이블을 key-value Map으로 변환
     */
    private Map<String, String> extractPropertyMap(Document doc) {

        Map<String, String> map = new HashMap<>();

        Elements rows = doc.select("table.lodList tbody tr");

        for (Element row : rows) {

            Element keyEl = row.selectFirst("td.tit a");
            Element valueEl = row.selectFirst("td:nth-child(2)");

            if (keyEl == null || valueEl == null) {
                continue;
            }

            String key = keyEl.text().trim();
            String value = clean(valueEl.text());

            if (!value.isEmpty()) {
                map.put(key, value);
            }
        }

        return map;
    }

    /**
     * 제목 추출
     */
    private String extractTitle(Document doc) {
        Element el = doc.selectFirst("div.titArea p");
        return el != null ? clean(el.ownText()) : null;
    }

    /**
     * Description 추출
     */
    private String extractDescription(Document doc) {
        Element el = doc.selectFirst("div.resultInfo dl dd");
        return el != null ? clean(el.text()) : null;
    }

    /**
     * 위/경도 숫자 파싱
     */
    private BigDecimal parseDecimal(String raw) {
        if (raw == null) return null;

        String num = raw.replaceAll("[^0-9.\\-]", "");
        return num.isEmpty() ? null : new BigDecimal(num);
    }

    /**
     * LOD 이미지 URL 추출
     */
    public List<String> extractImageUrls(Document doc) {
        return doc.select("a[href^=http][href*=/cms/resource/]")
                .stream()
                .map(e -> e.attr("href"))
                .distinct()
                .toList();
    }

    /**
     * 첫 번째 non-null / non-blank 값 반환
     */
    private String firstNonNull(String... values) {
        for (String v : values) {
            if (v != null && !v.isBlank()) {
                return v;
            }
        }
        return null;
    }

    /**
     * 공통 텍스트 정리
     */
    private String clean(String text) {
        if (text == null) return null;

        return text
                .replace("(@ko)", "")
                .replace("\u00A0", " ")
                .trim();
    }
}
