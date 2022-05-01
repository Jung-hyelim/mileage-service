package com.example.mileage.domain;

import com.example.mileage.enums.EventAction;

import javax.persistence.*;

/**
 * mileage_history : 마일리지 증감 히스토리 테이블 <br>
 * - 이벤트가 발생할 때마다 포인트 변화 이력을 저장한다. <br>
 */
@Entity
@Table(name = "mileage_history")
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

}
