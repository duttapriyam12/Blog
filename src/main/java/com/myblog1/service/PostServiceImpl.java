package com.myblog1.service;

import com.myblog1.entity.Post;
import com.myblog1.exception.PostNotFoundException;
import com.myblog1.payload.PostDto;
import com.myblog1.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    @Override
    public PostDto createPost(PostDto postDto) {
//        Post post = mapToEntity(postDto);
//        Post save = postRepository.save(post);
//        PostDto dto = mapToDto(save);
//        return dto ;

        Post save = postRepository.save(mapToEntity(postDto));
        return mapToDto(save);
    }

    @Override
    public List<PostDto> readAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        //List<Post> all = postRepository.findAll();
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(Sort.Direction.ASC,sortBy):Sort.by(Sort.Direction.DESC,sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> all = postRepository.findAll(pageable);
        List<Post> content = all.getContent();


        return all.stream().map(e->mapToDto(e)).collect(Collectors.toList());
    }

     @Override
     public PostDto readPostById(Long id) {
         Post post = postRepository.findById(id).orElseThrow(() ->
                 new PostNotFoundException("Post not found with id: " + id));
         return mapToDto(post);
     }

    @Override
    public PostDto updatePostById(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new PostNotFoundException("Post not found" + id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new PostNotFoundException("Post not found " + id));
         postRepository.deleteById(id);
    }

    @Override
    public Post getPostById(Long id) {
            return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found"));
        }
        @Override
        public void save(Post post) {
            postRepository.save(post);
        }



    Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        return post;
    }

   PostDto mapToDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setDescription(post.getDescription());
        return postDto;
    }
}
