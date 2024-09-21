package com.myblog1.controller;

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

     @DeleteMapping
    public ResponseEntity<String> deletePostById(@RequestParam Long id){
          postService.deletePostById(id);
          return new ResponseEntity<>("Post deleted", HttpStatus.OK);
     }



}
