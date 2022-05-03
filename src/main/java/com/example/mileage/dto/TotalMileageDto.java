package com.example.mileage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class TotalMileageDto {
    private String userId;
    private int totalPoint;
}
