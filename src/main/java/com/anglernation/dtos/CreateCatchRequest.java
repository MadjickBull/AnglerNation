package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Schema(description = "Create Catch Request for API v1")
public record CreateCatchRequest(
        @Schema(description = "Location ID", example = "123e4567-e89b-12d3-a456-426614174000") @NotNull UUID locationId,
        UUID rodId,
        UUID lureId,
        UUID lineId,
        @Schema(description = "Fish ID", example = "123e4567-e89b-12d3-a456-426614174000") @NotNull UUID fishId
) {
}