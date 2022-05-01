package com.example.mileage.domain;

import com.example.mileage.vo.MileageRequest;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * place_first_review : 장소별 첫 리뷰 <br>
 * - 리뷰 생성 시 장소별 첫리뷰인지 체크할때 사용한다. <br>
 * - 리뷰 생성 시 place_first_review 에 insert 한다. <br>
 * - insert 성공 시 해당장소의 첫리뷰이므로 보너스 점수 부여 / insert 실패 시 해당장소의 첫리뷰가 아니므로 보너스 점수 부여하지 않는다. <br>
 */
@Entity
@Table(name = "place_first_review", indexes = {
        @Index(name = "index_place_first_review_place_id", columnList = "placeId", unique = true)
})
@NoArgsConstructor
@ToString
public class PlaceFirstReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 36, nullable = false)
    private String placeId;

    @Column(length = 36, nullable = false)
    private String reviewId;

    public PlaceFirstReview(MileageRequest request) {
        this.placeId = request.getPlaceId();
        this.reviewId = request.getReviewId();
    }

}
