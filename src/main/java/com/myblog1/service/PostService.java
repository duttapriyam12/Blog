package com.myblog1.service;

import com.myblog1.payload.PostDto;

import java.util.List;

public interface PostService {


    PostDto createPost(PostDto postDto);

    List<PostDto> readAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto readPostById(Long id);

    PostDto updatePostById(Long id, PostDto dto);

    void deletePostById(Long id);
}
