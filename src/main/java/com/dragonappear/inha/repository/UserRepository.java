package com.dragonappear.inha.repository;

import com.dragonappear.inha.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
