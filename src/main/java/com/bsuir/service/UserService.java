package com.bsuir.service;

import com.bsuir.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUser();
    List<UserDTO> getAllUser(String exceptUsername);

    UserDTO getUserById(Long id);

    UserDTO disableUser(Long id);

    UserDTO saveUser(UserDTO userDTO);

    UserDTO getUserByUsername(String username);

    void updateIconUrl(String newImageUrl, String username);

    Integer getUsersCount();
    void updateRole(String[] roles, String username);
    Integer getUsersCountWithMoreThanOneProperty();
}