package com.bsuir.service.impl;

import com.bsuir.config.JwtService;
import com.bsuir.dto.auth.AuthenticationRequest;
import com.bsuir.dto.auth.AuthenticationResponse;
import com.bsuir.dto.auth.LogoutResponse;
import com.bsuir.dto.auth.RegisterRequest;
import com.bsuir.entity.Bookmark;
import com.bsuir.entity.Role;
import com.bsuir.entity.User;
import com.bsuir.entity.UserDetails;
import com.bsuir.exception.DuplicateException;
import com.bsuir.exception.RoleNotFoundException;
import com.bsuir.exception.UserNotFoundException;
import com.bsuir.repository.BookmarkRepository;
import com.bsuir.repository.RoleRepository;
import com.bsuir.repository.UserDetailsRepository;
import com.bsuir.repository.UserRepository;
import com.bsuir.service.AuthenticationService;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bsuir.constant.RentalPropertiesConstants.DefaultValue.DEFAULT_ICON_URL;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDetailsRepository userDetailRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ServletContext context;
    private final BookmarkRepository bookmarkRepository;

    @Override
    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateException("Ошибка! Логин уже занят.");
        }
        if(userRepository.existsByUserDetailsEmail(request.getEmail())) {
            throw new DuplicateException("Ошибка! Email уже занят.");
        }
        if(userRepository.existsByUserDetailsPhone(request.getPhone())) {
            throw new DuplicateException("Ошибка! Телефон уже занят.");
        }

        UserDetails userDetail = UserDetails.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .iconUrl(DEFAULT_ICON_URL)
                .build();
        Role role = roleRepository.findByName("USER")
                .orElseThrow(RoleNotFoundException::new);
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .userDetails(userDetail)
                .isActive(true)
                .allowPropertyCount(1)
                .totalPropertyCount(0)
                .roles(List.of(role))
                .build();
        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .build();
        userDetailRepository.save(userDetail);
        userRepository.save(user);
        bookmarkRepository.save(bookmark);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authentication(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException(request.getUsername()));
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public LogoutResponse logout() {
        return LogoutResponse.builder()
                .redirect(context.getContextPath() + "/home")
                .build();
    }
}