package com.viet.blog_api.service;

import com.viet.blog_api.dto.auth.LoginRequest;
import com.viet.blog_api.dto.auth.LoginResponse;
import com.viet.blog_api.dto.auth.RegisterRequest;

public interface AuthenticationService {

    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}
