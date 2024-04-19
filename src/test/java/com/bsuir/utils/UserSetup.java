package com.bsuir.utils;

import com.bsuir.dto.auth.AuthenticationResponse;
import com.bsuir.dto.auth.RegisterRequest;
import com.bsuir.repository.UserRepository;
import com.bsuir.service.AuthenticationService;

public class UserSetup {

    public static void registerUser(AuthenticationService authenticationService, UserRepository userRepository) {
        if(userRepository.existsByUsername("test")) {
            return;
        }

        RegisterRequest registerRequest = RegisterRequest.builder()
                .email("test@gmail.com")
                .phone("+375333333333")
                .firstName("test")
                .lastName("test")
                .username("test")
                .password("test")
                .build();
        AuthenticationResponse register = authenticationService.register(registerRequest);
        System.out.println(register);
    }
}