package com.viet.blog_api.exception.auth;

public class EmailExistsException extends RuntimeException {
    public EmailExistsException(String message) {
        super(message);
    }

    public EmailExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
