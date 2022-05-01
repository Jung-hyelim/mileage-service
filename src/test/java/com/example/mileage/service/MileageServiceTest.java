package com.example.mileage.service;

import com.example.mileage.enums.EventAction;
import com.example.mileage.enums.EventType;
import com.example.mileage.repository.MileageHistoryRepository;
import com.example.mileage.repository.MileageRepository;
import com.example.mileage.repository.PlaceFirstReviewRepository;
import com.example.mileage.vo.MileageRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MileageServiceTest {

    @Autowired private MileageService mileageService;

//    @Autowired private MileageRepository mileageRepository;
//    @Autowired private MileageHistoryRepository mileageHistoryRepository;
//    @Autowired private PlaceFirstReviewRepository placeFirstReviewRepository;

    @Test
    public void 리뷰생성이벤트_저장_테스트() {
        MileageRequest request = MileageRequest.builder()
                .type(EventType.REVIEW)
                .action(EventAction.ADD)
                .reviewId("review_id_uuid_1")
                .content("좋아요!")
                .attachedPhotoIds(Arrays.asList("photo_uuid_1", "photo_uuid_2"))
                .userId("user_id_uuid_1")
                .placeId("place_id_uuid_1")
                .build();

        mileageService.setMileage(request);

    }

}