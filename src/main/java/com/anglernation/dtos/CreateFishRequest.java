
package com.anglernation.dtos;

import com.anglernation.models.Fish.FishType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Create Fish Request for API v1")
public record CreateFishRequest(
        @Schema(description = "Fish type", example = "FRESHWATER") @NotNull FishType fishType,
        @Schema(description = "Fish name", example = "Trout") @NotBlank String name,
        @Schema(description = "Weight in lbs", example = "5.5") @Positive Double weight,
        @Schema(description = "Length in inches", example = "24.0") @PositiveOrZero Double size
) {
}