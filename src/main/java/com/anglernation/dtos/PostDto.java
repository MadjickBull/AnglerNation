
package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;

@Schema(description = "Post DTO for API v1")
public record PostDto(
        @Schema(description = "Unique identifier", example = "123e4567-e89b-12d3-a456-426614174000") UUID id,
        @Schema(description = "Post title", example = "My Fishing Trip") String title,
        @Schema(description = "Post description", example = "Went fishing at Lake Tahoe and caught a big trout!") String description,
        @Schema(description = "Author ID", example = "123e4567-e89b-12d3-a456-426614174000") UUID authorId,
        @Schema(description = "Created timestamp", example = "2024-01-15T10:30:00Z") Instant createdAt,
        @Schema(description = "Updated timestamp", example = "2024-01-15T11:00:00Z") Instant updatedAt
) {
}