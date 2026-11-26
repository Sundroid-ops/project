package com.demo.health_dekho_app.auth.DTO;

public record SignupResponse(
    Long customerId,
    String fullName,
    String email,
    String message
) {}
