package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByRoleName(String name);

    @Override
    void delete(Role role);
}
