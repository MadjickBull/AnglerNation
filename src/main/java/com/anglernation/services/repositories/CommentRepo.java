package com.anglernation.services.repositories;

import com.anglernation.models.comments.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepo extends JpaRepository<Comment, UUID> {
    List<Comment> findByPostId(UUID postId);
}