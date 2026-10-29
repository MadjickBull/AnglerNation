package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import com.anglernation.models.lures.LureType;
import com.anglernation.models.lures.LureBuoyancy;
import java.util.UUID;

@Schema(description = "Lure DTO for API v1")
public record LureDto(
    @Schema(description = "Unique identifier", example = "123e4567-e89b-12d3-a456-426614174000") UUID id,
    @Schema(description = "Lure type", example = "CRANKBait") LureType lureType,
    @Schema(description = "Lure name", example = "My Lure") String name,
    @Schema(description = "Lure color", example = "Chartreuse") String color,
    @Schema(description = "Lure size", example = "5.0") Double size,
    @Schema(description = "Lure weight in grams", example = "15.0") Double weight,
    @Schema(description = "Lure buoyancy", example = "FLOAT") LureBuoyancy lureBuoyancy,
    @Schema(description = "Owner UUID", example = "123e4567-e89b-12d3-a456-426614174000") UUID ownerId
) {
}