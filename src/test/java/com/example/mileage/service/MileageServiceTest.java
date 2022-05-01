package com.example.mileage.service;

import com.example.mileage.dto.TotalMileageDto;
import com.example.mileage.enums.EventAction;
import com.example.mileage.enums.EventType;
import com.example.mileage.vo.MileageRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@Slf4j
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


    @Test
    public void 리뷰수정이벤트_테스트() {

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

        MileageRequest request2 = MileageRequest.builder()
                .type(EventType.REVIEW)
                .action(EventAction.MOD)
                .reviewId("review_id_uuid_1")
                .content("좋아요!")
                .attachedPhotoIds(Arrays.asList("photo_uuid_2"))
                .userId("user_id_uuid_1")
                .placeId("place_id_uuid_1")
                .build();

        mileageService.setMileage(request2);

    }

    @Test
    public void 리뷰삭제이벤트_테스트() {

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

        MileageRequest request2 = MileageRequest.builder()
                .type(EventType.REVIEW)
                .action(EventAction.DELETE)
                .reviewId("review_id_uuid_1")
//                .content("좋아요!")
//                .attachedPhotoIds(Arrays.asList("photo_uuid_1", "photo_uuid_2"))
                .userId("user_id_uuid_1")
                .placeId("place_id_uuid_1")
                .build();

        mileageService.setMileage(request2);

    }

    @Test
    public void 마일리지조회() {
        String userId = "user_id_uuid_1";
        TotalMileageDto mileages = mileageService.getUserTotalMileage(userId);
        log.info("마일리지 조회 결과 userId={}, totalMileage={}", userId, mileages);
    }

}