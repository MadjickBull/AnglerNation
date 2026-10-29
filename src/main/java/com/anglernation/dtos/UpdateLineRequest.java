package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import com.anglernation.models.lines.LineType;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Update Line Request for API v1")
public record UpdateLineRequest(
    @Schema(description = "Line type", example = "MONOFILAMENT") LineType lineType,
    @Schema(description = "Line name", example = "My Line") String name,
    @Schema(description = "Line diameter in mm", example = "0.30") @Positive Double diameter,
    @Schema(description = "Breaking strength in kg", example = "15") @Positive Integer breakingStrength,
    @Schema(description = "Line color", example = "Clear") String color
) {
}