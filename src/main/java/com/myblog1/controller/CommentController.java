package com.myblog1.controller;

import com.myblog1.payload.CommentDto;
import com.myblog1.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    private CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestParam long postId, @RequestBody CommentDto commentDto){
        CommentDto dto = commentService.createComment(postId,commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@RequestParam long postId) {
        List<CommentDto> listOfComments = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(listOfComments, HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<CommentDto> updateComment(@RequestParam long postId, @RequestParam long commentId, @RequestBody CommentDto commentDto){
        CommentDto dto = commentService.updateComment(postId, commentId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteComment(@RequestParam long postId, @RequestParam long commentId) {
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
