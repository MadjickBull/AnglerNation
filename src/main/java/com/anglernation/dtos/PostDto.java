package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "Post DTO with front-end payload")
public class PostDto {

    @Schema(
            description = "Unique identifier for the post",
            example = "123e4567-e89b-12d3-a456-426614174000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID id;

    @Schema(
            description = "Title of the post",
            example = "My Fishing Trip",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String title;

    @Schema(
            description = "Content/description of the post",
            example = "Went fishing at Lake Powell and caught a big bass!"
    )
    private String description;

    @Schema(
            description = "Author of the post",
            example = "123e4567-e89b-12d3-a456-426614174000"
    )
    private UUID authorId;

    @Schema(
            description = "Date/time when the post was created",
            example = "2024-01-15T10:30:00Z"
    )
    private Instant createdAt;

    @Schema(
            description = "Date/time when the post was last updated",
            example = "2024-01-16T14:20:00Z"
    )
    private Instant updatedAt;

    public PostDto() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}