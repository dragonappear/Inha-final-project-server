package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken,Long> {
}
