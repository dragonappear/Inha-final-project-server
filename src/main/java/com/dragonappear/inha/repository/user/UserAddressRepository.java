package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddress,Long> {
}
