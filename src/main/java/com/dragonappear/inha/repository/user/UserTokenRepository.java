package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserTokenRepository extends JpaRepository<UserToken,Long> {
    @Query("select ut.token from UserToken ut where ut.user.id=:userId and ut.type=:type")
    String findTokenByUserIdAndType(@Param("userId") Long userId, @Param("type") String type);
}
