package com.myblog1.repository;

import com.myblog1.entity.Post;
import com.myblog1.payload.PostDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {


}