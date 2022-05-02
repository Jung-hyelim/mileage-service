package com.example.mileage.domain;

import com.example.mileage.dto.MileageDto;
import com.example.mileage.enums.EventAction;
import com.example.mileage.vo.MileageRequest;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * mileage_history : 마일리지 증감 히스토리 테이블 <br>
 * - 이벤트가 발생할 때마다 포인트 변화 이력을 저장한다. <br>
 */
@NoArgsConstructor
@ToString
@Entity
@Table(name = "mileage_history_back")
public class MileageHistoryBack extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private EventAction action;

    @Column(length = 36, nullable = false)
    private String userId;

    @Column(length = 36, nullable = false)
    private String reviewId;

    @Column
    private Integer contentReviewPoint;

    @Column
    private Integer photoReviewPoint;

    @Column
    private Integer firstPlaceReviewPoint;

    public MileageHistoryBack(MileageRequest request) {
        this.action = request.getAction();
        this.userId = request.getUserId();
        this.reviewId = request.getReviewId();
    }

    public MileageHistoryBack calculatePointByAddAction(MileageBack mileageBack) {
        this.contentReviewPoint = calculateChangedPoint(false, mileageBack.getHasContentReview());
        this.photoReviewPoint = calculateChangedPoint(false, mileageBack.getHasPhotoReview());
        this.firstPlaceReviewPoint = calculateChangedPoint(false, mileageBack.getIsFirstPlaceReview());
        return this;
    }

    public MileageHistoryBack calculatePointByModAction(MileageDto oldMileage, MileageBack newMileageBack) {
        this.contentReviewPoint = calculateChangedPoint(oldMileage.getHasContentReview(), newMileageBack.getHasContentReview());
        this.photoReviewPoint = calculateChangedPoint(oldMileage.getHasPhotoReview(), newMileageBack.getHasPhotoReview());
        this.firstPlaceReviewPoint = calculateChangedPoint(oldMileage.getIsFirstPlaceReview(), newMileageBack.getIsFirstPlaceReview());
        return this;
    }

    public MileageHistoryBack calculatePointByDeleteAction(MileageBack mileageBack) {
        this.contentReviewPoint = calculateChangedPoint(mileageBack.getHasContentReview(), false);
        this.photoReviewPoint = calculateChangedPoint(mileageBack.getHasPhotoReview(), false);
        this.firstPlaceReviewPoint = calculateChangedPoint(mileageBack.getIsFirstPlaceReview(), false);
        return this;
    }

    private int calculateChangedPoint(Boolean hasOldItem, Boolean hasNewItem) {
        if(hasOldItem == null) hasOldItem = false;
        if(hasNewItem == null) hasNewItem = false;

        if(hasOldItem == hasNewItem) return 0;
        else if(hasOldItem && !hasNewItem) return -1;
        else if(!hasOldItem && hasNewItem) return 1;
        return 0;
    }

    public boolean isChangedPoint() {
        if(this.contentReviewPoint == 0 && this.photoReviewPoint == 0 && this.firstPlaceReviewPoint == 0) return false;
        else return true;
    }
}
