package com.viet.blog_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.viet.blog_api.dto.post.PostRequest;
import com.viet.blog_api.entity.Post;
import com.viet.blog_api.entity.Tag;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "tags", source = "tags", qualifiedByName = "toTags")
    Post toPost(PostRequest request);

    @Named("toTags")
    default List<Tag> toTags(List<String> tags) {
        if (tags == null || tags.isEmpty())
            return List.of();
        return tags.stream().map(tag -> Tag.builder().name(tag).build()).toList();
    }
}
