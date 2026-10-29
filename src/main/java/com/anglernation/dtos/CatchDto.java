
package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;

@Schema(description = "Catch DTO for API v1")
public record CatchDto(
        @Schema(description = "Unique identifier", example = "123e4567-e89b-12d3-a456-426614174000") UUID id,
        @Schema(description = "Location ID", example = "123e4567-e89b-12d3-a456-426614174000") UUID locationId,
        @Schema(description = "Rod ID", example = "123e4567-e89b-12d3-a456-426614174000") UUID rodId,
        @Schema(description = "Lure ID", example = "123e4567-e89b-12d3-a456-426614174000") UUID lureId,
        @Schema(description = "Line ID", example = "123e4567-e89b-12d3-a456-426614174000") UUID lineId,
        @Schema(description = "Fish ID", example = "123e4567-e89b-12d3-a456-426614174000") UUID fishId,
        @Schema(description = "Angler ID", example = "123e4567-e89b-12d3-a456-426614174000") UUID anglerId,
        @Schema(description = "Created timestamp", example = "2024-01-15T10:30:00Z") Instant createdAt
) {
}