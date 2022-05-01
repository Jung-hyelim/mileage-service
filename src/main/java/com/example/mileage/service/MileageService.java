package com.example.mileage.service;

import com.example.mileage.domain.Mileage;
import com.example.mileage.domain.MileageHistory;
import com.example.mileage.domain.PlaceFirstReview;
import com.example.mileage.dto.MileageDto;
import com.example.mileage.dto.TotalMileageDto;
import com.example.mileage.repository.MileageHistoryRepository;
import com.example.mileage.repository.MileageRepository;
import com.example.mileage.repository.PlaceFirstReviewRepository;
import com.example.mileage.vo.MileageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MileageService {

    private static final ModelMapper mapper = new ModelMapper();

    private final MileageRepository mileageRepository;
    private final MileageHistoryRepository mileageHistoryRepository;
    private final PlaceFirstReviewRepository placeFirstReviewRepository;

    public void setMileage(MileageRequest request) {
        switch (request.getAction()) {
            case ADD:
                addMileage(request);
                break;
            case MOD:
                modifyMileage(request);
                break;
            case DELETE:
                deleteMileage(request);
                break;
        }
    }

    private void addMileage(MileageRequest request) {
        // 최초 장소에 대한 리뷰 Insert -> insert 성공시 보너스 점수 부여 / insert 실패시 보너스점수 없음.
        boolean isFirstPlaceReview = false;
        try {
            placeFirstReviewRepository.save(new PlaceFirstReview(request));
            isFirstPlaceReview = true;
        } catch (Exception e) {
            isFirstPlaceReview = false;
        }

        // 마일리지 저장
        Mileage mileage = new Mileage(request, isFirstPlaceReview);
        mileageRepository.save(mileage);
        log.debug("신규 마일리지 저장 = {}", mileage);

        // 마일리지 히스토리 저장
        MileageHistory mileageHistory = new MileageHistory(request).calculatePointByAddAction(mileage);
        mileageHistoryRepository.save(mileageHistory);
        log.debug("신규 마일리지 저장 히스토리 저장 = {}", mileageHistory);
    }

    private void modifyMileage(MileageRequest request) {
        // 기존 마일리지 정보 조회
        Mileage mileage = mileageRepository.findByUserIdAndPlaceIdAndReviewId(request.getUserId(), request.getPlaceId(), request.getReviewId()).orElseThrow();
        MileageDto oldMileageDto = mapper.map(mileage, MileageDto.class);
        log.info("변경 전 마일리지 dto = {}", oldMileageDto);

        // 마일리지 수정
        mileage.modifyAction(request);
        mileageRepository.save(mileage);
        log.debug("마일리지 수정 = {}", mileage);

        // 마일리지 히스토리에 변화된 포인트 저장
        MileageHistory mileageHistory = new MileageHistory(request).calculatePointByModAction(oldMileageDto, mileage);
        if(mileageHistory.isChangedPoint()) {
            mileageHistoryRepository.save(mileageHistory);
            log.debug("마일리지 수정 히스토리 저장 = {}", mileageHistory);
        }
    }

    private void deleteMileage(MileageRequest request) {
        // 기존 마일리지 정보 조회
        Mileage mileage = mileageRepository.findByUserIdAndPlaceIdAndReviewId(request.getUserId(), request.getPlaceId(), request.getReviewId()).orElseThrow();
        log.debug("마일리지 삭제 = {}", mileage);

        // 마일리지 히스토리에 변화된 포인트 저장
        MileageHistory mileageHistory = new MileageHistory(request).calculatePointByDeleteAction(mileage);
        mileageHistoryRepository.save(mileageHistory);
        log.debug("마일리지 삭제 히스토리 저장 = {}", mileageHistory);

        // 마일리지 삭제
        mileageRepository.delete(mileage);

        // 해당 장소에 대한 리뷰가 없으면 첫장소리뷰 삭제 / 해당 장소에 대한 리뷰가 있으면 삭제하지 않음
        boolean existsByPlaceId = mileageRepository.existsByPlaceId(mileage.getPlaceId());
        log.debug("placeId={}, 해당 장소에 리뷰가 있는지 여부={}", mileage.getPlaceId(), existsByPlaceId);
        if(!existsByPlaceId) {
            placeFirstReviewRepository.deleteByPlaceId(mileage.getPlaceId());
        }
    }

    public TotalMileageDto getMileages(String userId) {
        // 마일리지 리스트 조회
        List<Mileage> mileageList = mileageRepository.findAllByUserId(userId);

        // 포인트 계산
        int totalPoint = mileageList.stream().mapToInt(mileage -> {
            int point = 0;
            if (mileage.getHasContentReview()) point += 1;
            if (mileage.getHasPhotoReview()) point += 1;
            if (mileage.getIsFirstPlaceReview()) point += 1;
            return point;
        }).sum();

        return TotalMileageDto.builder()
                .userId(userId)
                .totalPoint(totalPoint)
                .build();
    }
}
