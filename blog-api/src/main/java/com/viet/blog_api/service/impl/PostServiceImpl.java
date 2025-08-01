package com.viet.blog_api.service.impl;

import org.springframework.stereotype.Service;

import com.viet.blog_api.dto.post.PostRequest;
import com.viet.blog_api.entity.Tag;
import com.viet.blog_api.mapper.PostMapper;
import com.viet.blog_api.repository.PostRepository;
import com.viet.blog_api.repository.TagRepository;
import com.viet.blog_api.service.PostService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    private Tag getTagInstanceInDB(String tagName) {
        Tag tag = this.tagRepository.findByName(tagName).orElse(null);

        if (tag != null)
            return tag;

        return this.tagRepository.save(Tag.builder().name(tagName).build());
    }

    @Override
    public void post(PostRequest request) {
        var post = postMapper.toPost(request);

        // init tag instance
        var tags = post.getTags().stream().map(tag -> getTagInstanceInDB(tag.getName())).toList();
        post.setTags(tags);

        this.postRepository.save(post);
    }

}
