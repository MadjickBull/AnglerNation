
package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "Update Catch Request for API v1")
public record UpdateCatchRequest(
        @Schema(description = "Location ID", example = "123e4567-e89b-12d3-a456-426614174000") UUID locationId,
        @Schema(description = "Rod ID", example = "123e4567-e89b-12d3-a456-426614174000") UUID rodId,
        @Schema(description = "Lure ID", example = "123e4567-e89b-12d3-a456-426614174000") UUID lureId,
        @Schema(description = "Line ID", example = "123e4567-e89b-12d3-a456-426614174000") UUID lineId,
        @Schema(description = "Fish ID") UUID fishId
) {
}