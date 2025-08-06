package com.viet.blog_api.exception;

import com.viet.blog_api.dto.exception.ExceptionResponse;

import lombok.Getter;

@Getter
public class CustomJwtException extends RuntimeException {
    private final ExceptionResponse response;

    public CustomJwtException(ExceptionResponse response) {
        super(response.getMessage());
        this.response = response;
    }
}
