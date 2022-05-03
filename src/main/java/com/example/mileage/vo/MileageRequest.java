package com.example.mileage.vo;

import com.example.mileage.enums.EventAction;
import com.example.mileage.enums.EventType;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class MileageRequest extends BaseRequest {

    @NotNull
    private String reviewId;

    private String content;

    private List<String> attachedPhotoIds;

    @NotNull
    private String placeId;

    @Builder
    public MileageRequest(@NotNull EventType type, @NotNull EventAction action, @NotNull String userId, String reviewId, String content, List<String> attachedPhotoIds, String placeId) {
        super(type, action, userId);
        this.reviewId = reviewId;
        this.content = content;
        this.attachedPhotoIds = attachedPhotoIds;
        this.placeId = placeId;
    }
}
