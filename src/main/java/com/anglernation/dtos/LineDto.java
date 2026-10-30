package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import com.anglernation.models.lines.LineType;
import java.util.UUID;

@Schema(description = "Line DTO for API v1")
public record LineDto(
    @Schema(description = "Unique identifier", example = "123e4567-e89b-12d3-a456-426614174000") UUID id,
    @Schema(description = "Line type", example = "MONOFILAMENT") LineType lineType,
    @Schema(description = "Line name", example = "My Line") String name,
    @Schema(description = "Line diameter in mm", example = "0.30") Double diameter,
    @Schema(description = "Breaking strength in kg", example = "15") Integer breakingStrength,
    @Schema(description = "Line color", example = "Clear") String color,
    @Schema(description = "Owner UUID", example = "123e4567-e89b-12d3-a456-426614174000") UUID ownerId
) {
}