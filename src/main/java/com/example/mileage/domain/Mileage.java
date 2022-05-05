package com.example.mileage.domain;

import com.example.mileage.enums.EventType;
import com.example.mileage.vo.ReviewEventRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * mileage : 마일리지 <br>
 * - 사용자의 이벤트 별 마일리지 내역 <br>
 */
@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "mileage", indexes = {
        @Index(name = "index_mileage_user_id_event_type_event_key", columnList = "userId, eventType, eventKey", unique = true),  // 사용자는 장소마다 리뷰를 1개만 작성 가능
        @Index(name = "index_mileage_event_type_event_key", columnList = "eventType, eventKey"),
})
public class Mileage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 36, nullable = false, updatable = false)
    private String userId;  // 사용자 id

    @Enumerated(EnumType.STRING)
    @Column(length = 12, nullable = false, updatable = false)
    private EventType eventType;    // 이벤트 타입

    @Column(length = 36, nullable = false, updatable = false)
    private String eventKey;    // 이벤트 타입별 키 { REVIEW 의 eventKey = reviewId ,,, }

    private boolean isDeleted = false;  // 삭제 여부

    public Mileage(ReviewEventRequest request) {
        this.userId = request.getUserId();
        this.eventType = request.getType();
        this.eventKey = request.getKey();
    }

    public void delete() {
        this.isDeleted = true;
    }
}
