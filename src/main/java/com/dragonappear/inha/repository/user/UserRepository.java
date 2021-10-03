package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
