package com.bsuir.service;

import com.bsuir.dto.auth.AuthenticationRequest;
import com.bsuir.dto.auth.AuthenticationResponse;
import com.bsuir.dto.auth.LogoutResponse;
import com.bsuir.dto.auth.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest registerRequest);
    AuthenticationResponse authentication(AuthenticationRequest authenticationRequest);
    LogoutResponse logout();
}