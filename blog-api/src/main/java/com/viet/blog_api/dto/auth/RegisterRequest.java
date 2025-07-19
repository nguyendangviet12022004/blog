package com.viet.blog_api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @Email(message = "Invalid email format") @NotBlank(message = "Email is required") String email,

        @NotBlank(message = "Password is required") String password,
        String firstName,
        String lastName) {
}
