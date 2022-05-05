package com.example.mileage.repository;

import com.example.mileage.domain.MileageHistory;
import com.example.mileage.enums.EventAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MileageHistoryRepository extends JpaRepository<MileageHistory, Long> {

    Optional<MileageHistory> findByMileageIdAndAction(Long mileageId, EventAction action);
}
