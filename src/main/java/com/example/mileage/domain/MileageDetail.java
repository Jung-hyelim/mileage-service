package com.example.mileage.domain;

import com.example.mileage.enums.PointType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * mileage_detail : 마일리지 상세 내역 <br>
 * - 마일리지 이벤트의 적립 포인트 상세 내역 <br>
 * - mileage 와 1:N 관계 <br>
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "mileage_detail")
public class MileageDetail extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long mileageId;

    @Enumerated(EnumType.STRING)
    @Column(length = 12, nullable = false)
    private PointType pointType;

    private int point;

    public MileageDetail(Long mileageId, PointType pointType) {
        this.mileageId = mileageId;
        this.pointType = pointType;
        this.point = this.pointType.point;
    }
}
