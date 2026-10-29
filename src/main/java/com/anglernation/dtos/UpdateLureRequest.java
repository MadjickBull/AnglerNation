package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import com.anglernation.models.lures.LureType;
import com.anglernation.models.lures.LureBuoyancy;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Update Lure Request for API v1")
public record UpdateLureRequest(
    @Schema(description = "Lure type", example = "CRANKBait") LureType lureType,
    @Schema(description = "Lure name", example = "My Lure") String name,
    @Schema(description = "Lure color", example = "Chartreuse") String color,
    @Schema(description = "Lure size", example = "5.0") @Positive Double size,
    @Schema(description = "Lure weight in grams", example = "15.0") @PositiveOrZero Double weight,
    @Schema(description = "Lure buoyancy", example = "FLOAT") LureBuoyancy lureBuoyancy
) {
}