package com.example.mileage.domain;

import com.example.mileage.enums.EventType;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * first_event_check : 첫번째 이벤트 값 체크 테이블 <br>
 * - 리뷰 이벤트의 경우, 장소별 첫리뷰인지 체크할때 사용한다. <br>
 * - 리뷰 생성 시 first_event_check 에 insert 한다. <br>
 * - insert 성공 시 해당장소의 첫리뷰이므로 보너스 점수 부여 / insert 실패 시 해당장소의 첫리뷰가 아니므로 보너스 점수 부여하지 않는다. <br>
 */
@NoArgsConstructor
@Entity
@Table(name = "first_event_check", indexes = {
        @Index(name = "index_first_event_check_unique", columnList = "eventType, eventKey", unique = true)
})
public class FirstEventCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 12, nullable = false, updatable = false)
    private EventType eventType;    // 이벤트 타입

    @Column(length = 36, nullable = false, updatable = false)
    private String eventKey;    // 이벤트 타입별 키

    public FirstEventCheck(EventType eventType, String eventKey) {
        this.eventType = eventType;
        this.eventKey = eventKey;
    }

}
