package com.example.mileage.service;

import com.example.mileage.dto.MileageDto;
import com.example.mileage.repository.MileageRepository;
import com.example.mileage.vo.MileageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MileageService {

//    private static final ObjectMapper mapper = new ObjectMapper();

    private final MileageRepository mileageRepository;

    public void setMileage(MileageRequest request) {
        // 0. ADD action 일때만 최초 장소에 대한 리뷰 insert / DELETE action 일때 최초 장소에 대한 리뷰 delete 하거나 다른리뷰가 있으면 지우지 않는다.

        // 1. 로직에 따라 마일리지 Insert, Update, Delete 한다.

        // 2. 유저의 마일리지 히스토리 테이블에 저장한다.


        switch (request.getAction()) {
            case ADD:
                break;
            case MOD:
                break;
            case DELETE:
                break;
        }
    }

    public MileageDto getMileages(String userId) {

        return null;
    }
}
