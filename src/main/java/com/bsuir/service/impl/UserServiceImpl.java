package com.bsuir.service.impl;

import com.bsuir.dto.UserDTO;
import com.bsuir.entity.Role;
import com.bsuir.entity.User;
import com.bsuir.entity.UserDetails;
import com.bsuir.exception.UserNotFoundException;
import com.bsuir.mapper.UserMapper;
import com.bsuir.repository.RoleRepository;
import com.bsuir.repository.UserDetailsRepository;
import com.bsuir.repository.UserRepository;
import com.bsuir.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final UserDetailsRepository userDetailsRepository;
    @Override
    public List<UserDTO> getAllUser() {
        List<User> userList = userRepository.findAll();
        return userMapper.toListOfDto(userList);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserDTO disableUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setIsActive(false);
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {

        User exisistingUser = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(()-> new UserNotFoundException(userDTO.getUsername()));

        UserDetails userDetail = exisistingUser.getUserDetails();
        userDetail.setEmail(userDTO.getEmail());
        userDetail.setFirstName(userDTO.getFirstName());
        userDetail.setLastName(userDTO.getLastName());
        userDetail.setPhone(userDTO.getPhone());
        userDetail.setIconUrl(userDTO.getIconUrl());
        userDetailsRepository.save(userDetail);

        return userMapper.toDto(userRepository.save(exisistingUser));
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public void updateIconUrl(String newImageUrl, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        user.getUserDetails()
                .setIconUrl(newImageUrl);
    }

    @Override
    public Integer getUsersCount() {
        return userRepository.findAllUserCount();
    }

    @Override
    public List<UserDTO> getAllUser(String exceptUsername) {
        return userRepository.findAll()
                .stream()
                .filter((user)->!exceptUsername.equals(user.getUsername()))
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public void updateRole(String[] roles, String username) {
        List<Role> selectedRole = roleRepository.findAllByNameIn(List.of(roles));
        User user = userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException(username));
        user.setRoles(selectedRole);
        userRepository.save(user);
    }

    @Override
    public Integer getUsersCountWithMoreThanOneProperty() {
        return userRepository.findAllUsersCountWithMoreThanOneProperty();
    }
}