package com.example.mileage.controller;

import com.example.mileage.dto.TotalMileageDto;
import com.example.mileage.service.MileageService;
import com.example.mileage.vo.MileageResponse;
import com.example.mileage.vo.ReviewEventRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
public class MileageController {

    private static final ModelMapper mapper = new ModelMapper();

    private final MileageService mileageService;

    @PostMapping({"/v1/mileage/review", "/events"})
    public String setMileage(@RequestBody @Valid ReviewEventRequest request) {
        mileageService.setMileage(request);
        return "SUCCESS";
    }

    @GetMapping("/v1/mileage/{userId}")
    public MileageResponse getMileage(@PathVariable String userId) {
        TotalMileageDto totalMileage = mileageService.getUserTotalMileage(userId);
        return mapper.map(totalMileage, MileageResponse.class);
    }
}
