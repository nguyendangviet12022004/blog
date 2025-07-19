package com.viet.blog_api.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.viet.blog_api.dto.auth.RegisterRequest;
import com.viet.blog_api.entity.Account;
import com.viet.blog_api.mapper.AccountMapper;
import com.viet.blog_api.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;

    @Override
    public void register(RegisterRequest request) {

        Account account = accountMapper.toAccount(request);
        account.setPassword(passwordEncoder.encode(request.password()));

    }

}
