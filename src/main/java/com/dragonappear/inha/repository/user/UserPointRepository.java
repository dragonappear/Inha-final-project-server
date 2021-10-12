package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserPointRepository extends JpaRepository<UserPoint,Long> {
    @Query("select u from UserPoint u where u.user.id=:userId")
    Optional<UserPoint> findByUserId(@Param("userId") Long userId);
}
