package com.bsuir.controller;

import com.bsuir.dto.auth.AuthenticationRequest;
import com.bsuir.dto.auth.AuthenticationResponse;
import com.bsuir.dto.auth.LogoutResponse;
import com.bsuir.dto.auth.RegisterRequest;
import com.bsuir.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authentication(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response
    ) {
        AuthenticationResponse authenticationResponse = authenticationService.authentication(request);
        Cookie cookie = new Cookie("tokens", authenticationResponse.getToken());
        cookie.setMaxAge(24*60*60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(authenticationResponse);
    }

    @GetMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("tokens", null);
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
        return ResponseEntity.ok(authenticationService.logout());
    }
}