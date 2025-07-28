package com.viet.blog_api.dto.auth;

public record LoginResponse(
        String accessToken,
        String refreshToken) {
}
