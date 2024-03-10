package com.bsuir.service.impl;

import com.bsuir.entity.Role;
import com.bsuir.repository.RoleRepository;
import com.bsuir.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public String[] getAllRolesAsStringArray() {
        return roleRepository.findAll()
                .stream()
                .map(Role::getName)
                .toArray(String[]::new);
    }
}