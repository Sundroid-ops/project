package com.demo.health_dekho_app.auth.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record SignupRequest(

    @NotBlank(message = "Full name is required")
    String fullName,

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    String email,

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must contain at least 8 characters")
    String password,

    @Past(message = "Date of birth must be in the past")
    LocalDate dob,

    String phone

) {}
