package com.viet.blog_api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @NotEmpty(message = "Email is required") @Email(message = "Email wrong format") String email,
        @NotEmpty(message = "Password is required") String password) {
}