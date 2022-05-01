package com.example.mileage.domain;

import com.example.mileage.enums.EventAction;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * mileage_history : 마일리지 증감 히스토리 테이블 <br>
 * - 이벤트가 발생할 때마다 포인트 변화 이력을 저장한다. <br>
 */
@Entity
@Table(name = "mileage_history")
@NoArgsConstructor
@ToString
public class MileageHistory extends BaseTimeEntity {
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

    public MileageHistory(EventAction action, Mileage mileage) {
        this.action = action;
        this.userId = mileage.getUserId();
        this.reviewId = mileage.getReviewId();
    }

    public MileageHistory calculatePointByAddAction(Mileage mileage) {
        this.contentReviewPoint = calculateChangedPoint(false, mileage.getHasContentReview());
        this.photoReviewPoint = calculateChangedPoint(false, mileage.getHasPhotoReview());
        this.firstPlaceReviewPoint = calculateChangedPoint(false, mileage.getIsFirstPlaceReview());
        return this;
    }

    public MileageHistory calculatePointByModAction(Mileage oldMileage, Mileage newMileage) {
        this.contentReviewPoint = calculateChangedPoint(oldMileage.getHasContentReview(), newMileage.getHasContentReview());
        this.photoReviewPoint = calculateChangedPoint(oldMileage.getHasPhotoReview(), newMileage.getHasPhotoReview());
        this.firstPlaceReviewPoint = calculateChangedPoint(oldMileage.getIsFirstPlaceReview(), newMileage.getIsFirstPlaceReview());
        return this;
    }

    public MileageHistory calculatePointByDeleteAction(Mileage mileage) {
        this.contentReviewPoint = calculateChangedPoint(mileage.getHasContentReview(), false);
        this.photoReviewPoint = calculateChangedPoint(mileage.getHasPhotoReview(), false);
        this.firstPlaceReviewPoint = calculateChangedPoint(mileage.getIsFirstPlaceReview(), false);
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
}