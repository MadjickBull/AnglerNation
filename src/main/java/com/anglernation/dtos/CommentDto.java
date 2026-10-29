package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "Comment DTO with front-end payload")
public class CommentDto {

    @Schema(
            description = "Unique identifier for the comment",
            example = "123e4567-e89b-12d3-a456-426614174000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID id;

    @Schema(
            description = "Content of the comment",
            example = "Great catch! Nice fish.",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String content;

    @Schema(
            description = "Author of the comment",
            example = "123e4567-e89b-12d3-a456-426614174000"
    )
    private UUID authorId;

    @Schema(
            description = "Post ID the comment belongs to",
            example = "123e4567-e89b-12d3-a456-426614174000"
    )
    private UUID postId;

    @Schema(
            description = "Date/time when the comment was created",
            example = "2024-01-15T10:30:00Z"
    )
    private Instant createdAt;

    public CommentDto() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}