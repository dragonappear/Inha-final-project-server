package com.dragonappear.inha.service.user;

import com.dragonappear.inha.domain.user.Role;

import java.util.List;

public interface RoleService {
    Role getRole(long id);

    List<Role> getRoles();

    void createRole(Role role);

    void deleteRole(long id);
}
