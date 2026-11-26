package com.demo.health_dekho_app.auth.DTO;

public record LoginResponse(
    String token,
    String tokenType,
    long expiresIn
) {}
