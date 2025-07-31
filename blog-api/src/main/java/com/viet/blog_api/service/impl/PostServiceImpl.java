package com.viet.blog_api.service.impl;

import org.springframework.stereotype.Service;

import com.viet.blog_api.dto.post.PostRequest;
import com.viet.blog_api.mapper.PostMapper;
import com.viet.blog_api.repository.PostRepository;
import com.viet.blog_api.service.PostService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final PostRepository postRepository;

    @Override
    public void post(PostRequest request) {
        var post = postMapper.toPost(request);
        this.postRepository.save(post);
    }

}
