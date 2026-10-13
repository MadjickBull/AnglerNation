package com.anglernation.services;

import com.anglernation.dtos.CreatePostRequest;
import com.anglernation.dtos.PostDto;
import com.anglernation.dtos.UpdatePostRequest;
import com.anglernation.models.posts.Post;
import com.anglernation.models.users.User;
import com.anglernation.services.repositories.PostRepo;
import com.anglernation.services.repositories.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepo postRepo;
    private final UserRepo userRepo;

    public PostService(PostRepo postRepo, UserRepo userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    private Post getPostOrThrow(UUID postId) {
        return postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
    }

    private User getUserOrThrow(UUID userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional
    public PostDto createPost(CreatePostRequest request, UUID authorId) {
        User author = getUserOrThrow(authorId);

        Post post = new Post();
        post.setTitle(request.title());
        post.setDescription(request.description());
        post.setAuthor(author);

        postRepo.save(post);
        return toDto(post);
    }

    public PostDto getPostById(UUID postId) {
        return toDto(getPostOrThrow(postId));
    }

    public List<PostDto> getAllPosts() {
        return postRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<PostDto> getPostsByAuthorId(UUID authorId) {
        getUserOrThrow(authorId);
        return postRepo.findByAuthorId(authorId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostDto updatePost(UUID postId, UpdatePostRequest request) {
        Post post = getPostOrThrow(postId);

        if (request.title() != null) post.setTitle(request.title());
        if (request.description() != null) post.setDescription(request.description());

        return toDto(postRepo.save(post));
    }

    @Transactional
    public PostDto deletePost(UUID postId) {
        Post post = getPostOrThrow(postId);
        postRepo.delete(post);
        return toDto(post);
    }

    private PostDto toDto(Post post) {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getAuthor().getId(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}