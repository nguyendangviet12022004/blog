package com.viet.blog_api.config;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.viet.blog_api.entity.Account;
import com.viet.blog_api.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class SecurityAuditorAware implements AuditorAware<Account> {

    private final AccountRepository accountRepository;

    @Override
    public Optional<Account> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Account account = accountRepository.findByEmail((authentication.getName())).orElse(null);
            if (account != null) {
                return Optional.of(account);
            }
        }
        return Optional.empty();
    }

}
