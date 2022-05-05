package com.example.mileage.service;

import com.example.mileage.domain.Mileage;
import com.example.mileage.domain.MileageDetail;
import com.example.mileage.domain.MileageHistory;
import com.example.mileage.dto.TotalMileageDto;
import com.example.mileage.enums.EventAction;
import com.example.mileage.enums.EventType;
import com.example.mileage.enums.PointType;
import com.example.mileage.repository.MileageDetailRepository;
import com.example.mileage.repository.MileageHistoryRepository;
import com.example.mileage.repository.MileageRepository;
import com.example.mileage.vo.ReviewEventRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MileageServiceTest {

    @Autowired private MileageService mileageService;

    @Autowired private MileageRepository mileageRepository;
    @Autowired private MileageDetailRepository mileageDetailRepository;
    @Autowired private MileageHistoryRepository mileageHistoryRepository;
//    @Autowired private PlaceFirstReviewRepository placeFirstReviewRepository;

    private final static EventType reviewEventType = EventType.REVIEW;
    private final static String userId = "test_user_id";
    private final static String reviewId = "test_review_id";
    private final static String content = "테스트 내용 입니다!";
    private final static String photoId = "test_photo_id";
    private final static String placeId = "test_place_uuid";
    private final static String existsPlaceId = "place_id_uuid_1";


    @Test
    public void 리뷰생성이벤트_내용만작성_포인트1점() {
        String prefix = "내용만작성한_테스트_";
        ReviewEventRequest request = ReviewEventRequest.builder()
                .type(reviewEventType)
                .action(EventAction.ADD)
                .reviewId(prefix + reviewId)
                .content(content)
                .userId(prefix + userId)
                .placeId(existsPlaceId)
                .build();
        mileageService.setMileage(request);

        final List<PointType> validPointType = Arrays.asList(PointType.CONTENT);
        int expectedChangedPoint = validPointType.stream().mapToInt(p -> p.point).sum();
        validateReviewAddEvent(request, validPointType, expectedChangedPoint);
    }

    @Test
    public void 리뷰생성이벤트_내용과사진작성_포인트2점() {
        String prefix = "내용과사진작성한_테스트_";
        ReviewEventRequest request = ReviewEventRequest.builder()
                .type(reviewEventType)
                .action(EventAction.ADD)
                .reviewId(prefix + reviewId)
                .content(content)
                .attachedPhotoIds(Arrays.asList(photoId))
                .userId(prefix + userId)
                .placeId(existsPlaceId)
                .build();
        mileageService.setMileage(request);

        final List<PointType> validPointType = Arrays.asList(PointType.CONTENT, PointType.PHOTO);
        int expectedChangedPoint = validPointType.stream().mapToInt(p -> p.point).sum();
        validateReviewAddEvent(request, validPointType, expectedChangedPoint);
    }

    @Test
    public void 리뷰생성이벤트_내용만작성_첫장소리뷰_포인트2점() {
        String prefix = "내용만작성_첫장소리뷰_테스트_";
        ReviewEventRequest request = ReviewEventRequest.builder()
                .type(reviewEventType)
                .action(EventAction.ADD)
                .reviewId(prefix + reviewId)
                .content(content)
                .userId(prefix + userId)
                .placeId(prefix + placeId)
                .build();
        mileageService.setMileage(request);

        final List<PointType> validPointType = Arrays.asList(PointType.CONTENT, PointType.FIRST_PLACE);
        int expectedChangedPoint = validPointType.stream().mapToInt(p -> p.point).sum();
        validateReviewAddEvent(request, validPointType, expectedChangedPoint);
    }

    @Test
    public void 리뷰생성이벤트_내용과사진작성_첫장소리뷰_포인트2점() {
        String prefix = "내용과사진작성_첫장소리뷰_테스트_";
        ReviewEventRequest request = ReviewEventRequest.builder()
                .type(reviewEventType)
                .action(EventAction.ADD)
                .reviewId(prefix + reviewId)
                .content(content)
                .attachedPhotoIds(Arrays.asList(photoId))
                .userId(prefix + userId)
                .placeId(prefix + placeId)
                .build();
        mileageService.setMileage(request);

        final List<PointType> validPointType = Arrays.asList(PointType.CONTENT, PointType.PHOTO, PointType.FIRST_PLACE);
        int expectedChangedPoint = validPointType.stream().mapToInt(p -> p.point).sum();
        validateReviewAddEvent(request, validPointType, expectedChangedPoint);
    }

    public void validateReviewAddEvent(ReviewEventRequest request, List<PointType> validPointType, int expectedChangedPoint) {

        // mileage
        Mileage mileage = mileageRepository.findUserMileage(request.getUserId(), request.getType(), request.getKey()).orElseThrow();
        assertNotNull(mileage);
        assertEquals(request.getUserId(), mileage.getUserId());
        assertEquals(request.getType(), mileage.getEventType());
        assertEquals(request.getPlaceId(), mileage.getEventKey());
        assertFalse(mileage.isDeleted());

        // mileage detail
        List<MileageDetail> mileageDetailList = mileageDetailRepository.findAllByMileageId(mileage.getId());
        assertNotNull(mileageDetailList);
        assertEquals(validPointType.size(), mileageDetailList.size());
        mileageDetailList.stream().forEach(mileageDetail -> {
            assertTrue(validPointType.contains(mileageDetail.getPointType()));
            assertEquals(mileageDetail.getPointType().point, mileageDetail.getPoint());
        });

        // mileage history
        MileageHistory mileageHistory = mileageHistoryRepository.findByMileageIdAndAction(mileage.getId(), request.getAction()).orElseThrow();
        assertNotNull(mileageHistory);
        assertEquals(mileage.getId(), mileageHistory.getMileageId());
        assertEquals(expectedChangedPoint, mileageHistory.getChangedPoint());
    }

    @Test
    public void 리뷰생성이벤트_한사용자_장소마다_리뷰1개만_() {

    }

//    @Test
//    public void 리뷰생성이벤트_저장_테스트() {
//        ReviewEventRequest request = ReviewEventRequest.builder()
//                .type(EventType.REVIEW)
//                .action(EventAction.ADD)
//                .reviewId("review_id_uuid_1")
//                .content("좋아요!")
//                .attachedPhotoIds(Arrays.asList("photo_uuid_1", "photo_uuid_2"))
//                .userId("user_id_uuid_1")
//                .placeId("place_id_uuid_1")
//                .build();
//
//        mileageService.setMileage(request);
//
//    }
//
//
//    @Test
//    public void 리뷰수정이벤트_테스트() {
//
//        ReviewEventRequest request = ReviewEventRequest.builder()
//                .type(EventType.REVIEW)
//                .action(EventAction.ADD)
//                .reviewId("review_id_uuid_1")
//                .content("좋아요!")
//                .attachedPhotoIds(Arrays.asList("photo_uuid_1", "photo_uuid_2"))
//                .userId("user_id_uuid_1")
//                .placeId("place_id_uuid_1")
//                .build();
//
//        mileageService.setMileage(request);
//
//        ReviewEventRequest request2 = ReviewEventRequest.builder()
//                .type(EventType.REVIEW)
//                .action(EventAction.MOD)
//                .reviewId("review_id_uuid_1")
//                .content("좋아요!")
//                .attachedPhotoIds(Arrays.asList("photo_uuid_2"))
//                .userId("user_id_uuid_1")
//                .placeId("place_id_uuid_1")
//                .build();
//
//        mileageService.setMileage(request2);
//
//    }
//
//    @Test
//    public void 리뷰삭제이벤트_테스트() {
//
//        ReviewEventRequest request = ReviewEventRequest.builder()
//                .type(EventType.REVIEW)
//                .action(EventAction.ADD)
//                .reviewId("review_id_uuid_1")
//                .content("좋아요!")
//                .attachedPhotoIds(Arrays.asList("photo_uuid_1", "photo_uuid_2"))
//                .userId("user_id_uuid_1")
//                .placeId("place_id_uuid_1")
//                .build();
//
//        mileageService.setMileage(request);
//
//        ReviewEventRequest request2 = ReviewEventRequest.builder()
//                .type(EventType.REVIEW)
//                .action(EventAction.DELETE)
//                .reviewId("review_id_uuid_1")
//                .userId("user_id_uuid_1")
//                .placeId("place_id_uuid_1")
//                .build();
//
//        mileageService.setMileage(request2);
//
//    }
//
    @Test
    public void 마일리지조회() {
        String userId = "user_id_uuid_1";
        TotalMileageDto mileages = mileageService.getUserTotalMileage(userId);
        log.info("마일리지 조회 결과 userId={}, totalMileage={}", userId, mileages);
    }

}