package com.bksproject.bksproject.Repository;

import com.bksproject.bksproject.Model.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {
    ResetPasswordToken findByToken(String token);
    @Query(value = "SELECT * FROM reset_password_token WHERE user_ID = :userId", nativeQuery = true)
    ResetPasswordToken findByUserIdReset(@Param("userId") Long userId);
}
