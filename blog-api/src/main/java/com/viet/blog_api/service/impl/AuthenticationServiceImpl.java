package com.viet.blog_api.service.impl;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.viet.blog_api.constant.AuthorityName;
import com.viet.blog_api.dto.auth.LoginRequest;
import com.viet.blog_api.dto.auth.LoginResponse;
import com.viet.blog_api.dto.auth.RegisterRequest;
import com.viet.blog_api.entity.Account;
import com.viet.blog_api.entity.Authority;
import com.viet.blog_api.exception.auth.EmailExistsException;
import com.viet.blog_api.mapper.AccountMapper;
import com.viet.blog_api.repository.AccountRepository;
import com.viet.blog_api.repository.AuthorityRepository;
import com.viet.blog_api.service.AuthenticationService;
import com.viet.blog_api.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final AuthorityRepository authorityRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private Authority getAuthorityInstanceInDB(Authority authority) {
        if (!authorityRepository.existsByAuthorityName(authority.getAuthorityName())) {
            return authorityRepository.save(authority);
        }
        return authorityRepository.findByAuthorityName(authority.getAuthorityName())
                .orElseThrow(() -> new IllegalStateException("Authority not found"));
    }

    @Override
    public void register(RegisterRequest request) {

        // Check if email already exists
        if (accountRepository.existsByEmail(request.email())) {
            throw new EmailExistsException("Email already exists");
        }

        Account account = accountMapper.toAccount(request);

        // set default authorities if not provided
        if (account.getAuthorities() == null || account.getAuthorities().isEmpty()) {
            account.setAuthorities(
                    List.of(Authority.builder().authorityName(AuthorityName.ROLE_USER).build()));
        }

        // hash password
        account.setPassword(passwordEncoder.encode(request.password()));

        // init authorities
        List<Authority> authorities = account.getAuthorityEntities()
                .stream()
                .map(auth -> getAuthorityInstanceInDB(auth))
                .toList();
        account.setAuthorities(authorities);

        // save account to db
        accountRepository.save(account);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        String accessToken = jwtUtil.generateAccessToken(authentication);
        String refreshToken = jwtUtil.generateRefreshToken(authentication);

        return new LoginResponse(accessToken, refreshToken);
    }

}
