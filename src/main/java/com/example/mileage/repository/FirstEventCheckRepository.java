package com.example.mileage.repository;

import com.example.mileage.domain.FirstEventCheck;
import com.example.mileage.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FirstEventCheckRepository extends JpaRepository<FirstEventCheck, Long> {

    @Modifying
    @Transactional
    @Query("delete from FirstEventCheck where eventType = :eventType and eventKey = :eventKey")
    void deleteEvent(@Param("eventType") EventType eventType, @Param("eventKey") String eventKey);
}
