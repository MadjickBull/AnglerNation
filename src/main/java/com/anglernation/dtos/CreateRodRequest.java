package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import com.anglernation.models.rods.RodType;
import com.anglernation.models.rods.RodPower;
import com.anglernation.models.rods.RodAction;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Create Rod Request for API v1")
public record CreateRodRequest(
    @Schema(description = "Rod type", example = "SPINNING") @NotNull RodType rodType,
    @Schema(description = "Rod name", example = "My Rod") @NotBlank String name,
    @Schema(description = "Rod length in cm", example = "240.0") @Positive Double length,
    @Schema(description = "Rod weight in grams", example = "120.0") @PositiveOrZero Double weight,
    @Schema(description = "Rod power", example="MEDIUM") @NotNull RodPower rodPower,
    @Schema(description = "Rod action", example="FAST") @NotNull RodAction rodAction
) {
}