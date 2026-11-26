package com.demo.health_dekho_app.auth.Controller;

import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.health_dekho_app.auth.DTO.LoginRequest;
import com.demo.health_dekho_app.auth.DTO.LoginResponse;
import com.demo.health_dekho_app.auth.DTO.SignupRequest;
import com.demo.health_dekho_app.auth.DTO.SignupResponse;
import com.demo.health_dekho_app.auth.Security.CustomerPrincipal;
import com.demo.health_dekho_app.auth.Service.AuthService;
import com.demo.health_dekho_app.auth.Service.CurrentUserService;
import com.demo.health_dekho_app.auth.Service.CustomerRegistrationService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final CustomerRegistrationService customerRegistrationService;
    private final CurrentUserService currentUserService;

    public AuthController(AuthService authService, CustomerRegistrationService customerRegistrationService, CurrentUserService currentUserService) {
        this.authService = authService;
        this.customerRegistrationService = customerRegistrationService;
        this.currentUserService = currentUserService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(
            @Valid @RequestBody SignupRequest request) {

        SignupResponse response =
                customerRegistrationService.signup(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {

        CustomerPrincipal customer =
                currentUserService.getCurrentUser();

        return ResponseEntity.ok(Map.of(
                "customerId", customer.getId(),
                "email", customer.getUsername(),
                "message", "Authentication successful"
        ));
    }
}
