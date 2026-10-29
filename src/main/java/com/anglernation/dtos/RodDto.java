package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import com.anglernation.models.rods.RodType;
import com.anglernation.models.rods.RodPower;
import com.anglernation.models.rods.RodAction;
import java.util.UUID;

@Schema(description = "Rod DTO for API v1")
public record RodDto(
    @Schema(description = "Unique identifier", example = "123e4567-e89b-12d3-a456-426614174000") UUID id,
    @Schema(description = "Rod type", example = "SPINNING") RodType rodType,
    @Schema(description = "Rod name", example = "My Rod") String name,
    @Schema(description = "Rod length in cm", example = "240.0") Double length,
    @Schema(description = "Rod weight in grams", example = "120.0") Double weight,
    @Schema(description = "Rod power", example="MEDIUM") RodPower rodPower,
    @Schema(description = "Rod action", example="FAST") RodAction rodAction,
    @Schema(description = "Owner UUID", example = "123e4567-e89b-12d3-a456-426614174000") UUID ownerId
) {
}