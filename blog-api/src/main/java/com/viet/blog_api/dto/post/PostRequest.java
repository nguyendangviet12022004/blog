package com.viet.blog_api.dto.post;

import jakarta.validation.constraints.NotBlank;

public record PostRequest(
        @NotBlank(message = "title is required") String title,
        @NotBlank(message = "content is required") String content) {

}
