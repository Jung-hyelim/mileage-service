package com.example.mileage.service;

import com.example.mileage.domain.MileageDetail;
import com.example.mileage.dto.TotalMileageDto;
import com.example.mileage.repository.MileageDetailRepository;
import com.example.mileage.repository.MileageRepository;
import com.example.mileage.vo.BaseRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MileageService {

    private final MileageRepository mileageRepository;
    private final MileageDetailRepository mileageDetailRepository;

    private final EventServiceFactory eventServiceFactory;

    public void setMileage(BaseRequest request) {
        EventService eventService = eventServiceFactory.getEventService(request.getType());

        switch (request.getAction()) {
            case ADD:
                eventService.addEvent(request);
                break;
            case MOD:
                eventService.modifyEvent(request);
                break;
            case DELETE:
                eventService.deleteEvent(request);
                break;
        }
    }

    public TotalMileageDto getUserTotalMileage(String userId) {
        int totalPoint = 0;

        // 사용자의 마일리지 리스트 조회
        List<Long> userMileageIds = mileageRepository.findIdsByUserId(userId);

        if(userMileageIds != null && !userMileageIds.isEmpty()) {
            // 사용자의 마일리지 상세 리스트 조회
            List<MileageDetail> userMileageDetailList = mileageDetailRepository.findAllByMileageIds(userMileageIds);

            // 포인트 계산
            totalPoint = userMileageDetailList.stream().mapToInt(MileageDetail::getPoint).sum();
        }

        return TotalMileageDto.builder()
                .userId(userId)
                .totalPoint(totalPoint)
                .build();
    }

}
