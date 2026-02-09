package com.multi.travel.domain.place.repository;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : UnifiedPlaceView
 * @since : 26. 2. 9. 월요일
 **/


public interface UnifiedPlaceView {
    Long getPlaceId();
    Long getContentId();
    String getTitle();
    Integer getAreaCode();
    Integer getContentTypeId();
    String getSource();      // PLACE / SEED
}
