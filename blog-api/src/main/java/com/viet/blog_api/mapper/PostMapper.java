package com.viet.blog_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.viet.blog_api.dto.post.PostRequest;
import com.viet.blog_api.entity.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "id", ignore = true)
    Post toPost(PostRequest request);
}
