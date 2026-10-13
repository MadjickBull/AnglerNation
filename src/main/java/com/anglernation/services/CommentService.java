package com.anglernation.services;

import com.anglernation.dtos.CommentDto;
import com.anglernation.dtos.CreateCommentRequest;
import com.anglernation.dtos.UpdateCommentRequest;
import com.anglernation.models.comments.Comment;
import com.anglernation.models.posts.Post;
import com.anglernation.models.users.User;
import com.anglernation.services.repositories.CommentRepo;
import com.anglernation.services.repositories.PostRepo;
import com.anglernation.services.repositories.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepo commentRepo;
    private final PostRepo postRepo;
    private final UserRepo userRepo;

    public CommentService(CommentRepo commentRepo, PostRepo postRepo, UserRepo userRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    private Comment getCommentOrThrow(UUID commentId) {
        return commentRepo.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
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
    public CommentDto createComment(CreateCommentRequest request, UUID authorId, UUID postId) {
        User author = getUserOrThrow(authorId);
        Post post = getPostOrThrow(postId);

        Comment comment = new Comment();
        comment.setContent(request.content());
        comment.setAuthor(author);
        comment.setPost(post);

        commentRepo.save(comment);
        return toDto(comment);
    }

    public CommentDto getCommentById(UUID commentId) {
        return toDto(getCommentOrThrow(commentId));
    }

    public List<CommentDto> getCommentsByPostId(UUID postId) {
        getPostOrThrow(postId);
        return commentRepo.findByPostId(postId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto updateComment(UUID commentId, UpdateCommentRequest request) {
        Comment comment = getCommentOrThrow(commentId);

        if (request.content() != null) comment.setContent(request.content());

        return toDto(commentRepo.save(comment));
    }

    @Transactional
    public CommentDto deleteComment(UUID commentId) {
        Comment comment = getCommentOrThrow(commentId);
        commentRepo.delete(comment);
        return toDto(comment);
    }

    private CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getAuthor().getId(),
                comment.getPost().getId(),
                comment.getCreatedAt()
        );
    }
}