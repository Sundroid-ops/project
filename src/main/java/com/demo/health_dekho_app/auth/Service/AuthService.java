package com.demo.health_dekho_app.auth.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.demo.health_dekho_app.auth.DTO.LoginRequest;
import com.demo.health_dekho_app.auth.DTO.LoginResponse;
import com.demo.health_dekho_app.auth.Security.CustomerPrincipal;
import com.demo.health_dekho_app.auth.Security.JwtService;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.email(),
                                request.password()
                        )
                );

        CustomerPrincipal customer =
                (CustomerPrincipal) authentication.getPrincipal();

        String token = jwtService.generateToken(customer);

        return new LoginResponse(
                token,
                "Bearer",
                jwtService.getExpirationSeconds()
        );
    }
}