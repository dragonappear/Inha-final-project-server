package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserNotificationRepository extends JpaRepository<UserNotification,Long> {

    @Query("select un from UserNotification un where un.user.id=:userId order by createdDate desc")
    List<UserNotification> findAllByUserId(Long userId);
}
