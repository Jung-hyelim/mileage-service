package com.example.mileage.repository;

import com.example.mileage.domain.MileageDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MileageDetailRepository extends JpaRepository<MileageDetail, Long> {
    List<MileageDetail> findAllByMileageId(Long mileageId);

    @Modifying
    @Transactional
    @Query("delete from MileageDetail md where md.mileageId = :mileageId")
    void deleteAllByMileageId(Long mileageId);
}
