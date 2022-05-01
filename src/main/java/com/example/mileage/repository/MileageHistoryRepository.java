package com.example.mileage.repository;

import com.example.mileage.domain.MileageHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MileageHistoryRepository extends JpaRepository<MileageHistory, Long> {
}
