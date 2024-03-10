package com.bsuir.service;

import com.bsuir.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    String[] getAllRolesAsStringArray();
}