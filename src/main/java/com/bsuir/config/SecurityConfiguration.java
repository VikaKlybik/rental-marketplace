package com.bsuir.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final static String[] ALLOWED_URL_FOR_ALL = new String[]{
            "/auth/**",
            "/home",
            "/css/**",
            "/img/**",
            "/js/**",
            "/image/**",
            "/image",
            "/**"
    };
    private final static String[] ALLOWED_URL_FOR_AUTHENTICATED = new String[]{

    };
    private final static String[] ALLOWED_URL_FOR_USER = new String[]{

    };
    private final static String[] ALLOWED_URL_FOR_MANAGER = new String[]{

    };
    private final static String[] ALLOWED_URL_FOR_ADMIN = new String[]{

    };
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(ALLOWED_URL_FOR_ALL).permitAll()
//                            requestMatchers(ALLOWED_URL_FOR_AUTHENTICATED).hasAnyAuthority(roleService.getAllRolesAsStringArray())
//                            .requestMatchers(ALLOWED_URL_FOR_USER).hasAnyAuthority("USER")
//                            .requestMatchers(ALLOWED_URL_FOR_MANAGER).hasAnyAuthority("MANAGER")
//                            .requestMatchers(ALLOWED_URL_FOR_ADMIN).hasAnyAuthority("ADMIN")
                            .anyRequest()
                            .authenticated();
                })
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}