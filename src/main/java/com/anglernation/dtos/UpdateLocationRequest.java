
package com.anglernation.dtos;

import com.anglernation.models.locations.LocationVisibility;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Update Location Request for API v1")
public record UpdateLocationRequest(
        @Schema(description = "Location name", example = "Updated Lake Tahoe") String name,
        @Schema(description = "Visibility", example = "PRIVATE") LocationVisibility visibility,
        @Schema(description = "Altitude in meters", example = "1700.0") @PositiveOrZero Double altitude,
        Double longitude,
        Double latitude
) {
}