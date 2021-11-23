package com.dragonappear.inha.service.user;

import com.dragonappear.inha.domain.user.UserNotification;
import com.dragonappear.inha.repository.user.UserNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserNotificationService {
    private final UserNotificationRepository userNotificationRepository;

    @Transactional
    public Long save(UserNotification userNotification) {
        UserNotification save = userNotificationRepository.save(userNotification);
        return save.getId();
    }

    public List<UserNotification> findAllByUserId(Long userId) {
        List<UserNotification> list = userNotificationRepository.findAllByUserId(userId);
        return list;
    }

}
