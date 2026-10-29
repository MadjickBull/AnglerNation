
package com.anglernation.dtos;

import com.anglernation.models.locations.LocationVisibility;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Create Location Request for API v1")
public record CreateLocationRequest(
        @Schema(description = "Location name", example = "Lake Tahoe") String name,
        @Schema(description = "Visibility", example = "PUBLIC") @NotNull LocationVisibility visibility,
        @Schema(description = "Altitude in meters", example = "1800.0") @PositiveOrZero Double altitude,
        @Schema(description = "Longitude", example = "-119.97") Double longitude,
        @Schema(description = "Latitude", example = "39.14") Double latitude
) {
}