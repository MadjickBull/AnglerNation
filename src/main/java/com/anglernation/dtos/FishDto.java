
package com.anglernation.dtos;

import com.anglernation.models.Fish.FishType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "Fish DTO for API v1")
public record FishDto(
        @Schema(description = "Unique identifier", example = "123e4567-e89b-12d3-a456-426614174000") UUID id,
        @Schema(description = "Fish type", example = "FRESHWATER") FishType fishType,
        @Schema(description = "Fish name", example = "Trout") String name,
        @Schema(description = "Weight in lbs", example = "5.5") Double weight,
        @Schema(description = "Length in inches", example = "24.0") Double size
) {
}