package com.example.mileage.repository;

import com.example.mileage.domain.PlaceFirstReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PlaceFirstReviewRepository extends JpaRepository<PlaceFirstReview, Long> {

    @Transactional
    void deleteByPlaceId(String placeId);

}
