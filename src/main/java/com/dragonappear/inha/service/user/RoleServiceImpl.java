package com.dragonappear.inha.service.user;

import com.dragonappear.inha.domain.user.Role;
import com.dragonappear.inha.repository.user.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;


    /**
     * CREATE
     */

    @Transactional
    @Override
    public void createRole(Role role) {
        roleRepository.save(role);
    }

    /**
     * READ
     */

    @Override
    public Role getRole(long id) {
        return roleRepository.findById(id).orElse(new Role());
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }


    /**
     * DELETE
     */

    @Transactional
    @Override
    public void deleteRole(long id) {
        roleRepository.deleteById(id);
    }
}
