package com.anglernation.controllers;

import com.anglernation.dtos.CreatePostRequest;
import com.anglernation.dtos.PostDto;
import com.anglernation.dtos.UpdatePostRequest;
import com.anglernation.services.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody CreatePostRequest request, @RequestParam UUID authorId) {
        PostDto created = postService.createPost(request, authorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable UUID id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<PostDto>> getPostsByAuthor(@PathVariable UUID authorId) {
        return ResponseEntity.ok(postService.getPostsByAuthorId(authorId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable UUID id, @Valid @RequestBody UpdatePostRequest request) {
        return ResponseEntity.ok(postService.updatePost(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PostDto> deletePost(@PathVariable UUID id) {
        return ResponseEntity.ok(postService.deletePost(id));
    }
}