
package com.anglernation.dtos;

import com.anglernation.models.Fish.FishType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Update Fish Request for API v1")
public record UpdateFishRequest(
        @Schema(description = "Fish type", example = "FRESHWATER") FishType fishType,
        @Schema(description = "Fish name", example = "Updated Trout") String name,
        @Schema(description = "Weight in lbs", example = "6.0") @Positive Double weight,
        @Schema(description = "Length in inches", example = "25.0") @PositiveOrZero Double size
) {
}