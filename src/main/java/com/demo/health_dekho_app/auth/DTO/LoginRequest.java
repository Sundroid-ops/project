package com.demo.health_dekho_app.auth.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

    @NotBlank(message = "Customer Email ID cannot be empty")
    @Email(message = "Customer Email is invalid")
    String email,

    @NotBlank(message = "Customer Password cannot be empty")
    String password

) {}
