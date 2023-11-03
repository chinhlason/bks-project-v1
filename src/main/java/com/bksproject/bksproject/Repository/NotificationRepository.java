package com.bksproject.bksproject.Repository;

import com.bksproject.bksproject.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findById(Long id);

    @Query(value = "SELECT * FROM notification WHERE receiver_id = :userId", nativeQuery = true)
    List<Notification> getNotificationByReceiverID(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM notification WHERE type = :type", nativeQuery = true)
    List<Notification> getNotificationByType(@Param("type") String type);
}
