package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.UserAccount;
import com.dragonappear.inha.domain.user.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    @Query("select ua from UserAccount ua where ua.user.id=:userId")
    Optional<UserAccount> findByUserId(@Param("userId") Long userId);
}
