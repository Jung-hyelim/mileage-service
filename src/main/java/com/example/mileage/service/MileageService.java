package com.example.mileage.service;

import com.example.mileage.domain.Mileage;
import com.example.mileage.domain.MileageHistory;
import com.example.mileage.domain.PlaceFirstReview;
import com.example.mileage.dto.MileageDto;
import com.example.mileage.repository.MileageHistoryRepository;
import com.example.mileage.repository.MileageRepository;
import com.example.mileage.repository.PlaceFirstReviewRepository;
import com.example.mileage.vo.MileageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MileageService {

//    private static final ObjectMapper mapper = new ObjectMapper();

    private final MileageRepository mileageRepository;
    private final MileageHistoryRepository mileageHistoryRepository;
    private final PlaceFirstReviewRepository placeFirstReviewRepository;

    public void setMileage(MileageRequest request) {
        // 0. ADD action 일때만 최초 장소에 대한 리뷰 insert / DELETE action 일때 최초 장소에 대한 리뷰 delete 하거나 다른리뷰가 있으면 지우지 않는다.
        // 1. 로직에 따라 마일리지 Insert, Update, Delete 한다.
        // 2. 유저의 마일리지 히스토리 테이블에 저장한다.


        switch (request.getAction()) {
            case ADD:
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
                log.info("mileage = {}", mileage);
                mileageRepository.save(mileage);

                // 마일리지 히스토리 저장
                MileageHistory mileageHistory = new MileageHistory(request.getAction(), mileage).calculatePointByAddAction(mileage);
                mileageHistoryRepository.save(mileageHistory);
                break;
            case MOD:
                // 기존 마일리지 정보 조회

                // 마일리지 수정

                // 마일리지 히스토리에 변화된 포인트 저장
                break;
            case DELETE:
                // 마일리지 삭제

                // 마일리지 히스토리에 변화된 포인트 저장

                // 해당 장소에 대한 리뷰가 없으면 첫장소리뷰 삭제 / 해당 장소에 대한 리뷰가 있으면 삭제하지 않음
                break;
        }
    }

    public MileageDto getMileages(String userId) {

        return null;
    }
}
