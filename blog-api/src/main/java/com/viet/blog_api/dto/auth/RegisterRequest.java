package com.viet.blog_api.dto.auth;

import java.util.List;

import com.viet.blog_api.constant.AuthorityName;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
                @Email(message = "Invalid email format") @NotBlank(message = "Email is required") String email,

                @NotBlank(message = "Password is required") String password,
                String firstName,
                String lastName,
                List<AuthorityName> authorities) {
}
