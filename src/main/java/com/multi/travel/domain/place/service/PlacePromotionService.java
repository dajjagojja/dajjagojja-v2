package com.multi.travel.domain.place.service;

import com.multi.travel.domain.place.entity.Place;
import com.multi.travel.domain.place.img.PlaceImageSource;
import com.multi.travel.domain.place.img.entity.PlaceImage;
import com.multi.travel.domain.place.lod.client.LodClient;
import com.multi.travel.domain.place.lod.dto.LodPlaceData;
import com.multi.travel.domain.place.repository.PlaceRepository;
import com.multi.travel.domain.seed.entity.PlaceSeed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : PlacePromotionService
 * @since : 26. 2. 9. 월요일
 **/


@Service
@RequiredArgsConstructor
public class PlacePromotionService {

    private final PlaceRepository placeRepository;
    private final LodClient lodClient;

    @Transactional
    public Place promoteFromSeed(PlaceSeed seed) {

        LodPlaceData lodData = lodClient.fetch(seed.getContentId());

        Place place = Place.fromSeed(seed);
        place.updateFromLod(lodData.toUpdateDto());

        List<String> imageUrls = lodData.getImageUrls();
        if (imageUrls != null && !imageUrls.isEmpty()) {
            for (int i = 0; i < imageUrls.size(); i++) {
                place.getImages().add(
                        PlaceImage.builder()
                                .place(place)
                                .imageUrl(imageUrls.get(i))
                                .isMain(i == 0)
                                .source(PlaceImageSource.LOD_INIT)
                                .build()
                );
            }
        }

        return placeRepository.save(place);
    }
}
