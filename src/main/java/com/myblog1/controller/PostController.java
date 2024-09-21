package com.myblog1.controller;

import com.myblog1.entity.Post;
import com.myblog1.payload.PostDto;
import com.myblog1.service.PostService;
import jakarta.validation.Valid;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
       private PostService postService;
      public PostController(PostService postService) {

          this.postService = postService;
     }
     @PostMapping("/create")
     public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto,BindingResult result ) {
         if(result.hasErrors()) {
             return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);

         }
          return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
     }
     //http://localhost:8080/api/v1/posts?pageNo=0&pageSize=3
     @GetMapping
     public ResponseEntity<List<PostDto>> readAllPost(
            @RequestParam(name = "pageNo",defaultValue = "0", required = false) int pageNo,
            @RequestParam(name="pageSize",defaultValue = "3",required =false) int pageSize,
            @RequestParam(name="sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(name="sortDir",defaultValue = "title",required = false) String sortDir

     ){
          List<PostDto> dto = postService.readAllPost(pageNo, pageSize, sortBy,sortDir);
          return new ResponseEntity<>(dto, HttpStatus.OK);
     }

     @GetMapping("/get/{id}")
     public ResponseEntity<PostDto> readPostById(@PathVariable Long id){
          PostDto dto = postService.readPostById(id);
          return new ResponseEntity<>(dto, HttpStatus.OK);
     }

     @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePostById(@PathVariable Long id,  @Valid @RequestBody PostDto postDto,
                                                  BindingResult result){
          if(result.hasErrors()) {
              return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.OK);

          }
         PostDto dto = postService.updatePostById(id, postDto);
         return new ResponseEntity<>(dto, HttpStatus.OK);
     }

    @PutMapping("/{id}/like")
    public ResponseEntity<String> likePost(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        post.setLikeCount(post.getLikeCount() + 1); // Increment the like count
        postService.save(post); // Save the updated post
        return ResponseEntity.ok("Post liked!");
    }

    @PutMapping("/{id}/dislike")
    public ResponseEntity<String> unlikePost(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        // Ensure like count doesn't go below zero
        if (post.getLikeCount() > 0) {
            post.setLikeCount(post.getLikeCount() - 1); // Decrement the like count
            postService.save(post); // Save the updated post
            return ResponseEntity.ok("Post disliked!");
        } else {
            return ResponseEntity.ok("Post has no likes to remove.");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePostById(@RequestParam Long id){
          postService.deletePostById(id);
          return new ResponseEntity<>("Post deleted", HttpStatus.OK);
     }



}
