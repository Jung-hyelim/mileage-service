package com.example.mileage.repository;

import com.example.mileage.domain.Mileage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MileageRepository extends JpaRepository<Mileage, Long> {

    Optional<Mileage> findByUserIdAndPlaceIdAndReviewId(String userId, String placeId, String reviewId);

    boolean existsByPlaceId(String placeId);

}
