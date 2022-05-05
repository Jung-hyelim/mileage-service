package com.example.mileage.repository;

import com.example.mileage.domain.Mileage;
import com.example.mileage.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MileageRepository extends JpaRepository<Mileage, Long> {

    @Query("select m from Mileage m where m.userId = :userId and m.eventType = :eventType and m.eventKey = :eventKey and m.isDeleted = false")
    Optional<Mileage> findUserMileage(@Param("userId") String userId, @Param("eventType") EventType eventType, @Param("eventKey") String eventKey);

    boolean existsByEventTypeAndEventKeyAndIsDeletedIsFalse(@Param("eventType") EventType eventType, @Param("eventKey") String eventKey);

    @Query("select m.id from Mileage m where m.userId = :userId")
    List<Long> findIdsByUserId(String userId);
}
