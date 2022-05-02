package com.example.mileage.domain;

import com.example.mileage.enums.EventAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * mileage_history : 마일리지의 포인트 증감 히스토리 <br>
 * - 마일리지 이벤트 발생 시 포인트 변화 이력을 저장한다. <br>
 * - mileage 와 1:N 관계 <br>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "mileage_history")
public class MileageHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long mileageId;

    @Enumerated(EnumType.STRING)
    @Column(length = 12, nullable = false)
    private EventAction action; // 액션

    private int changedPoint;  // 증감 포인트

}
