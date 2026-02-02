package com.multi.travel.domain.seed.batch;

import com.multi.travel.domain.seed.repository.PlaceSeedRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : PlaceSeedInitializer
 * @since : 26. 2. 2. 월요일
 **/


@Component
@RequiredArgsConstructor
@Profile("local") // local 환경에서만 최초 seed 자동 수집
public class PlaceSeedInitializer {

    private final PlaceSeedRepository repository;
    private final PlaceSeedCollector collector;

    @PostConstruct
    public void init() {

        if (repository.count() == 0) {
            collector.collectAll();
        }
    }
}