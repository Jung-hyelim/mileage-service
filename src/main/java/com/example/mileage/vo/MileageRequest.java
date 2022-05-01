package com.example.mileage.vo;

import com.example.mileage.enums.EventAction;
import com.example.mileage.enums.EventType;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
public class MileageRequest {
    @NotNull
    private EventType type;

    @NotNull
    private EventAction action;

    @NotNull
    private String reviewId;

    private String content;

    private List<String> attachedPhotoIds;

    @NotNull
    private String userId;

    @NotNull
    private String placeId;

}
