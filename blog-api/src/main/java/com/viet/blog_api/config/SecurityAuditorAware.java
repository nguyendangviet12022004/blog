package com.viet.blog_api.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.viet.blog_api.entity.Account;

public class SecurityAuditorAware implements AuditorAware<Account> {

    @SuppressWarnings("null")
    @Override
    public Optional<Account> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return Optional.of(Account.builder().email(authentication.getName()).build());
        }
        return Optional.empty();
    }

}
