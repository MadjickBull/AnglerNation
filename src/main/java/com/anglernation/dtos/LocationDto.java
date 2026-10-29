
package com.anglernation.dtos;

import com.anglernation.models.locations.LocationVisibility;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "Location DTO for API v1")
public record LocationDto(
        @Schema(description = "Unique identifier", example = "123e4567-e89b-12d3-a456-426614174000") UUID id,
        @Schema(description = "Location name", example = "Lake Tahoe") String name,
        @Schema(description = "Visibility", example = "PUBLIC") LocationVisibility visibility,
        @Schema(description = "Altitude in meters", example = "1800.0") Double altitude,
        @Schema(description = "Longitude", example = "-119.97") Double longitude,
        @Schema(description = "Latitude", example = "39.14") Double latitude
) {
}