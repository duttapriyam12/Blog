package com.myblog1.service;

import com.myblog1.entity.Comment;
import com.myblog1.entity.Post;
import com.myblog1.exception.PostNotFoundException;
import com.myblog1.payload.CommentDto;
import com.myblog1.repository.CommentRepository;
import com.myblog1.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Post not found with this id: " + postId));
        Comment comment = maptoEntity(commentDto);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        return mapToDto(savedComment);

    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        if (comments.isEmpty()) {
            throw new PostNotFoundException("No comments found for post with id: " + postId);
        }

        List<CommentDto> dtos = comments.stream()
                .map(comment -> mapToDto(comment))
                .collect(Collectors.toList());
        return dtos;
    }



    Comment maptoEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        comment.setName(commentDto.getName());

        return comment;
    }

    CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setBody(comment.getBody());
        commentDto.setEmail(comment.getEmail());
        commentDto.setName(comment.getName());
        return commentDto;
    }
}
