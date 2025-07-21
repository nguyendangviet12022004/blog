package com.viet.blog_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viet.blog_api.constant.AuthorityName;
import com.viet.blog_api.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    boolean existsByAuthorityName(AuthorityName authorityName);

    Optional<Authority> findByAuthorityName(AuthorityName authorityName);

}
