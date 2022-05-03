package com.example.mileage.domain;

import com.example.mileage.vo.MileageRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;


/**
 * mileage : 마일리지 <br>
 * - 사용자 리뷰의 마일리지 적립 여부를 저장한다. <br>
 */
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "mileage_back", indexes = {
        @Index(name = "index_mileage_user_id_place_id", columnList = "userId, placeId", unique = true),  // 사용자는 장소마다 리뷰를 1개만 작성 가능
        @Index(name = "index_mileage_place_id", columnList = "placeId"),
})
public class MileageBack extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 36, nullable = false)
    private String userId;

    @Column(length = 36, nullable = false)
    private String reviewId;

    @Column(length = 36, nullable = false)
    private String placeId;

    private Boolean hasContentReview;

    private Boolean hasPhotoReview;

    private Boolean isFirstPlaceReview;

    public MileageBack(MileageRequest request, boolean isFirstPlaceReview) {
        this.userId = request.getUserId();
        this.reviewId = request.getReviewId();
        this.placeId = request.getPlaceId();
        this.hasContentReview = hasString(request.getContent());
        this.hasPhotoReview = hasList(request.getAttachedPhotoIds());
        this.isFirstPlaceReview = isFirstPlaceReview;
    }

    public void modifyAction(MileageRequest request) {
        this.hasContentReview = hasString(request.getContent());
        this.hasPhotoReview = hasList(request.getAttachedPhotoIds());
    }

    private boolean hasString(String content) {
        return !content.isBlank() && content.length() >= 1;
    }

    private boolean hasList(List list) {
        return list != null && list.size() >= 1;
    }
}
