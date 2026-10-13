package com.anglernation.dtos;

import com.anglernation.models.locations.LocationVisibility;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateLocationRequest(
        String name,
        LocationVisibility visibility,
        @PositiveOrZero Double altitude,
        Double longitude,
        Double latitude
) {
}