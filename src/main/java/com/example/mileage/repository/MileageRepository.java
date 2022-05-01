package com.example.mileage.repository;

import com.example.mileage.domain.Mileage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MileageRepository extends JpaRepository<Mileage, Long> {
}
