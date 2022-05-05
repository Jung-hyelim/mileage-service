package com.example.mileage.vo;

import com.example.mileage.enums.EventAction;
import com.example.mileage.enums.EventType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class ReviewEventRequest extends BaseRequest {

    @NotNull
    private String reviewId;

    private String content;

    private List<String> attachedPhotoIds;

    @NotNull
    private String placeId;

    @Builder
    public ReviewEventRequest(@NotNull EventType type, @NotNull EventAction action, @NotNull String userId, String reviewId, String content, List<String> attachedPhotoIds, String placeId) {
        super(type, action, userId, placeId);
        this.reviewId = reviewId;
        this.content = content;
        this.attachedPhotoIds = attachedPhotoIds;
        this.placeId = placeId;
    }

    public boolean hasContent() {
        return content != null &&!content.isBlank() && content.length() >= 1;
    }

    public boolean hasPhotos() {
        return attachedPhotoIds != null && attachedPhotoIds.size() >= 1;
    }
}
