package com.example.mileage.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MileageDto {
    private String userId;
    private String reviewId;
    private String placeId;
    private Boolean hasContentReview;
    private Boolean hasPhotoReview;
    private Boolean isFirstPlaceReview;

}
