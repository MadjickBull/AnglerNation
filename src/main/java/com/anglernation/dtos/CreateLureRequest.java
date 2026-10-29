package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import com.anglernation.models.lures.LureType;
import com.anglernation.models.lures.LureBuoyancy;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Create Lure Request for API v1")
public record CreateLureRequest(
    @Schema(description = "Lure type", example = "CRANKBait") @NotNull LureType lureType,
    @Schema(description = "Lure name", example = "My Lure") @NotBlank String name,
    @Schema(description = "Lure color", example = "Chartreuse") @NotBlank String color,
    @Schema(description = "Lure size", example = "5.0") @Positive Double size,
    @Schema(description = "Lure weight in grams", example = "15.0") @PositiveOrZero Double weight,
    @Schema(description = "Lure buoyancy", example = "FLOAT") @NotNull LureBuoyancy lureBuoyancy
) {
}