package com.viet.blog_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.viet.blog_api.constant.AuthorityName;
import com.viet.blog_api.dto.auth.RegisterRequest;
import com.viet.blog_api.entity.Account;
import com.viet.blog_api.entity.Authority;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "authorities", source = "authorities", qualifiedByName = "ToAuthroities")
    Account toAccount(RegisterRequest request);

    @Named("ToAuthroities")
    default List<Authority> toAuthroities(List<AuthorityName> authorities) {
        if (authorities == null || authorities.isEmpty()) {
            return List.of();
        }
        return authorities.stream()
                .map(auth -> Authority.builder().authorityName(auth).build()).toList();
    }
}
