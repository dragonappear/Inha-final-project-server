package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.UserCardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserCardInfoRepository extends JpaRepository<UserCardInfo,Long> {

    @Query("select uci from UserCardInfo uci where uci.user.id =:userId")
    List<UserCardInfo> findAllByUserId(@Param("userId") Long userId);

    @Query("select uci from UserCardInfo uci where uci.user.id=:userId and uci.id=:userCardInfoId")
    Optional<UserCardInfo> findByUserIdAndCardInfoId(@Param("userId")Long userId, @Param("userCardInfoId")Long userCardInfoId);
}
