package com.viet.blog_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.viet.blog_api.dto.auth.RegisterRequest;
import com.viet.blog_api.entity.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "avatarUrl", ignore = true)
    Account toAccount(RegisterRequest request);
}
