package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {


    Optional<User> findByEmail(String email);

    List<User> findByEmailOrUserTel(String email, String userTel);
}
