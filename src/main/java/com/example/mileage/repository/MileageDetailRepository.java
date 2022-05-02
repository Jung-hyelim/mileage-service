package com.example.mileage.repository;

import com.example.mileage.domain.MileageDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MileageDetailRepository extends JpaRepository<MileageDetail, Long> {
}
