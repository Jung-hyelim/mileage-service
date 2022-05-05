package com.example.mileage.service;

import com.example.mileage.domain.FirstEventCheck;
import com.example.mileage.domain.Mileage;
import com.example.mileage.domain.MileageDetail;
import com.example.mileage.domain.MileageHistory;
import com.example.mileage.enums.EventType;
import com.example.mileage.enums.PointType;
import com.example.mileage.repository.FirstEventCheckRepository;
import com.example.mileage.repository.MileageDetailRepository;
import com.example.mileage.repository.MileageHistoryRepository;
import com.example.mileage.repository.MileageRepository;
import com.example.mileage.vo.BaseRequest;
import com.example.mileage.vo.ReviewEventRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewEventService implements EventService {

    private final MileageRepository mileageRepository;
    private final MileageDetailRepository mileageDetailRepository;
    private final MileageHistoryRepository mileageHistoryRepository;
    private final FirstEventCheckRepository firstEventCheckRepository;

    @Override
    public EventType getEventType() {
        return EventType.REVIEW;
    }

    @Override
    public void addEvent(BaseRequest baseRequest) {
        ReviewEventRequest request = (ReviewEventRequest) baseRequest;

        // 최초 장소에 대한 리뷰 Insert -> insert 성공시 보너스 점수 부여 / insert 실패시 보너스점수 없음.
        boolean isFirstPlaceReview = false;
        try {
            firstEventCheckRepository.save(new FirstEventCheck(request.getType(), request.getKey()));
            isFirstPlaceReview = true;
        } catch (Exception e) {
            isFirstPlaceReview = false;
        }

        // 마일리지 저장
        Mileage mileage = new Mileage(request);
        mileageRepository.save(mileage);

        int point = 0;

        // 마일리지 상세 저장
        if(request.hasContent()) {
            MileageDetail contentDetail = new MileageDetail(mileage.getId(), PointType.CONTENT);
            mileageDetailRepository.save(contentDetail);
            point += contentDetail.getPoint();
        }
        if(request.hasPhotos()) {
            MileageDetail photoDetail = new MileageDetail(mileage.getId(), PointType.PHOTO);
            mileageDetailRepository.save(photoDetail);
            point += photoDetail.getPoint();
        }
        if(isFirstPlaceReview) {
            MileageDetail firstPlaceReviewDetail = new MileageDetail(mileage.getId(), PointType.FIRST_PLACE);
            mileageDetailRepository.save(firstPlaceReviewDetail);
            point += firstPlaceReviewDetail.getPoint();
        }

        // 마일리지 히스토리 저장
        MileageHistory mileageHistory = MileageHistory.builder()
                .mileageId(mileage.getId())
                .action(request.getAction())
                .changedPoint(point)
                .build();
        mileageHistoryRepository.save(mileageHistory);
    }

    @Override
    public void modifyEvent(BaseRequest baseRequest) {
        ReviewEventRequest request = (ReviewEventRequest) baseRequest;

        // 기존 마일리지 정보 조회
        Mileage mileage = mileageRepository.findUserMileage(request.getUserId(), request.getType(), request.getKey()).orElseThrow();
        log.debug("기존 마일리지 정보 조회 = {}", mileage);

        List<MileageDetail> mileageDetailList = mileageDetailRepository.findAllByMileageId(mileage.getId());
        log.debug("마일리지 상세 정보 조회 = {}", mileageDetailList.toString());

        // 기존 마일리지 상세 정보와 request 정보 비교하여 변화 감지 & 상세 Update
        int point = 0;
        Set<PointType> pointTypeSet = new HashSet<>();

        // 있다가 삭제된 경우
        point += mileageDetailList.stream().mapToInt(mileageDetail -> {
            pointTypeSet.add(mileageDetail.getPointType());

            int changedPoint = 0;
            switch (mileageDetail.getPointType()) {
                case CONTENT:
                    if(!request.hasContent()) {
                        changedPoint -= mileageDetail.getPoint();
                        mileageDetailRepository.deleteById(mileageDetail.getId());
                    }
                    break;
                case PHOTO:
                    if(!request.hasPhotos()) {
                        changedPoint -= mileageDetail.getPoint();
                        mileageDetailRepository.deleteById(mileageDetail.getId());
                    }
                    break;
            }
            return changedPoint;
        }).sum();
        // 없다가 추가된 경우
        if(!pointTypeSet.contains(PointType.CONTENT) && request.hasContent()) {
            MileageDetail contentDetail = new MileageDetail(mileage.getId(), PointType.CONTENT);
            mileageDetailRepository.save(contentDetail);
            point += contentDetail.getPoint();
        }
        if(!pointTypeSet.contains(PointType.PHOTO) && request.hasPhotos()) {
            MileageDetail photoDetail = new MileageDetail(mileage.getId(), PointType.PHOTO);
            mileageDetailRepository.save(photoDetail);
            point += photoDetail.getPoint();
        }

        // 변화에 대한 포인트 증감 히스토리 저장
        if(point != 0) {
            MileageHistory mileageHistory = MileageHistory.builder()
                    .mileageId(mileage.getId())
                    .action(request.getAction())
                    .changedPoint(point)
                    .build();
            mileageHistoryRepository.save(mileageHistory);
        }
    }

    @Override
    public void deleteEvent(BaseRequest baseRequest) {
        ReviewEventRequest request = (ReviewEventRequest) baseRequest;

        // 기존 마일리지 정보 조회
        Mileage mileage = mileageRepository.findUserMileage(request.getUserId(), request.getType(), request.getKey()).orElseThrow();
        log.debug("기존 마일리지 정보 조회 = {}", mileage);

        List<MileageDetail> mileageDetailList = mileageDetailRepository.findAllByMileageId(mileage.getId());
        log.debug("마일리지 상세 정보 조회 = {}", mileageDetailList.toString());

        // 변화에 대한 포인트 증감 히스토리 저장
        int point = -1 * mileageDetailList.stream().mapToInt(MileageDetail::getPoint).sum();
        MileageHistory mileageHistory = MileageHistory.builder()
                .mileageId(mileage.getId())
                .action(request.getAction())
                .changedPoint(point)
                .build();
        mileageHistoryRepository.save(mileageHistory);

        // 마일리지 삭제처리
        mileageDetailRepository.deleteAllByMileageId(mileage.getId());
        mileage.delete();
        mileageRepository.save(mileage);

        // 해당 장소에 대한 리뷰가 없으면 첫장소리뷰 삭제 / 해당 장소에 대한 리뷰가 있으면 삭제하지 않음
        boolean isExistsUniqueEvent = mileageRepository.existsByEventTypeAndEventKeyAndIsDeletedIsFalse(mileage.getEventType(), mileage.getEventKey());
        if(!isExistsUniqueEvent) {
            log.debug("첫장소리뷰 삭제 key={}", mileage.getEventKey());
            firstEventCheckRepository.deleteEvent(mileage.getEventType(), mileage.getEventKey());
        }
    }

}
