
package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;

@Schema(description = "Comment DTO for API v1")
public record CommentDto(
        @Schema(description = "Unique identifier", example = "123e4567-e89b-12d3-a456-426614174000") UUID id,
        @Schema(description = "Comment content", example = "Great catch!") String content,
        @Schema(description = "Author ID", example = "123e4567-e89b-12d3-a456-426614174000") UUID authorId,
        @Schema(description = "Post ID", example = "123e4567-e89b-12d3-a456-426614174000") UUID postId,
        @Schema(description = "Created timestamp", example = "2024-01-15T10:30:00Z") Instant createdAt
) {
}