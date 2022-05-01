package com.example.mileage.controller;

import com.example.mileage.dto.MileageDto;
import com.example.mileage.dto.TotalMileageDto;
import com.example.mileage.service.MileageService;
import com.example.mileage.vo.MileageRequest;
import com.example.mileage.vo.MileageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
public class MileageController {
    private final MileageService mileageService;

    @PostMapping({"/v1/mileages", "/events"})
    public void setMileages(@RequestBody @Valid MileageRequest request) {
        mileageService.setMileage(request);
    }

    @GetMapping("/v1/mileages/{userId}")
    public MileageResponse getMileages(@PathVariable String userId) {
        TotalMileageDto totalMileage = mileageService.getMileages(userId);
        return null;
    }
}
