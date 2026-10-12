package com.anglernation.services.repositories;

import com.anglernation.models.posts.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepo extends JpaRepository<Post, UUID> {
    List<Post> findByAuthorId(UUID authorId);
}