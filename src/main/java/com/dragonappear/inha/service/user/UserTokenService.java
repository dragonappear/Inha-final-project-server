package com.dragonappear.inha.service.user;

import com.dragonappear.inha.domain.user.UserToken;
import com.dragonappear.inha.repository.user.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserTokenService {
    private final UserTokenRepository userTokenRepository;

    /**
     * CREATE
     */
    public Long save(UserToken userToken) {
        UserToken token = userTokenRepository.save(userToken);
        return token.getId();
    }
}
