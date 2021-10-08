package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}
