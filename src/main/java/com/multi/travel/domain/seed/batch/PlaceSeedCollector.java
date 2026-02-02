package com.multi.travel.domain.seed.batch;

import com.multi.travel.domain.seed.dto.AreaBasedResponse;
import com.multi.travel.domain.seed.dto.AreaItem;
import com.multi.travel.domain.seed.entity.PlaceSeed;
import com.multi.travel.domain.seed.enums.SeedStatus;
import com.multi.travel.domain.seed.repository.PlaceSeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : PlaceSeedCollector
 * @since : 26. 2. 2. 월요일
 **/


@Component
@RequiredArgsConstructor
public class PlaceSeedCollector {

    private static final int NUM_OF_ROWS = 100;
    private static final DateTimeFormatter MODIFIED_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final PlaceSeedApiClient apiClient;
    private final PlaceSeedRepository repository;

    private static final List<Integer> AREA_CODES = List.of(
            1, 2, 3, 4, 5, 6, 7, 8, 31, 32, 33, 34, 35, 36, 37, 38, 39
    );

    private static final List<Integer> CONTENT_TYPES = List.of(
            12, // 관광지
            32, // 숙박
            39  // 음식점
    );

    @Transactional
    public void collectAll() {

        for (int areaCode : AREA_CODES) {
            for (int contentType : CONTENT_TYPES) {

                int pageNo = 1;
                int totalCount;

                do {
                    AreaBasedResponse response =
                            apiClient.fetchAreaBasedList(areaCode, contentType, pageNo, NUM_OF_ROWS);

                    var items = response.getItems();
                    totalCount = response.getTotalCount();

                    for (AreaItem item : items) {
                        saveIfNotExists(item, areaCode, contentType);
                    }

                    pageNo++;
                } while ((pageNo - 1) * NUM_OF_ROWS < totalCount);
            }
        }
    }

    private void saveIfNotExists(AreaItem item, int areaCode, int contentType) {

        Long contentId = Long.valueOf(item.getContentId());

        if (repository.existsByContentId(contentId)) {
            return;
        }

        try {
            PlaceSeed seed = PlaceSeed.builder()
                    .contentId(contentId)
                    .contentTypeId(contentType)
                    .areaCode(areaCode)
                    .sigunguCode(item.getSigunguCode())
                    .title(item.getTitle())
                    .latitude(item.getLatitude())
                    .longitude(item.getLongitude())
                    .thumbnailUrl(item.getFirstImage())
                    .sourceModifiedAt(parseModifiedTime(item.getModifiedTime()))
                    .status(SeedStatus.COLLECTED)
                    .collectedAt(LocalDateTime.now())
                    .build();

            repository.save(seed);

        } catch (Exception e) {

            PlaceSeed failedSeed = PlaceSeed.builder()
                    .contentId(contentId)
                    .contentTypeId(contentType)
                    .areaCode(areaCode)
                    .title(item.getTitle())
                    .status(SeedStatus.FAILED)
                    .failReason(e.getClass().getSimpleName())
                    .collectedAt(LocalDateTime.now())
                    .build();

            repository.save(failedSeed);
        }
    }


    private LocalDateTime parseModifiedTime(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return LocalDateTime.parse(value, MODIFIED_TIME_FORMATTER);
    }
}
