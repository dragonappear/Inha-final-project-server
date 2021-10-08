package com.dragonappear.inha.service.user;

import com.dragonappear.inha.domain.user.UserPoint;
import com.dragonappear.inha.repository.user.UserPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserPointService {
    private final UserPointRepository userPointRepository;


}
