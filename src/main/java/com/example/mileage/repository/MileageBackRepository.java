package com.example.mileage.repository;

import com.example.mileage.domain.MileageBack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MileageBackRepository extends JpaRepository<MileageBack, Long> {

    Optional<MileageBack> findByUserIdAndPlaceIdAndReviewId(String userId, String placeId, String reviewId);

    boolean existsByPlaceId(String placeId);

    List<MileageBack> findAllByUserId(String userId);

}
