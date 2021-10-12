package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserAddressRepository extends JpaRepository<UserAddress,Long> {

    @Query("select ua from UserAddress ua where ua.user.id=:userId")
    List<UserAddress> findByUserId(@Param("userId") Long userId);

    @Query("select ua from UserAddress ua where ua.user.id=:userId and ua.id=:userAddressId")
    Optional<UserAddress> findByUserIdAndUserAddressId(@Param("userId")Long userId, @Param("userAddressId") Long userAddressId);
}
