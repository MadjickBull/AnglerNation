package com.anglernation.dtos;

import com.anglernation.models.locations.LocationVisibility;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateLocationRequest(
        String name,
        @NotNull LocationVisibility visibility,
        @PositiveOrZero Double altitude,
        Double longitude,
        Double latitude
) {
}