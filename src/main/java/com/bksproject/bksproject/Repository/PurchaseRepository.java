package com.bksproject.bksproject.Repository;

import com.bksproject.bksproject.Model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    @Query(value = "SELECT * FROM purchase WHERE user_id = :userId", nativeQuery = true)
    List<Purchase> findPurchaseByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM purchase WHERE user_id = :courseId", nativeQuery = true)
    List<Purchase> findPurchaseByCourseId(@Param("courseId") Long courseId);

    @Query(value = "SELECT * FROM purchase WHERE user_id = :userId AND course_id = :courseId LIMIT 1", nativeQuery = true)
    Purchase findPurchaseByUserIdAndCourseId(@Param("userId") Long userId, @Param("courseId") Long courseId);

    Purchase findPurchaseById(Long id);
}
