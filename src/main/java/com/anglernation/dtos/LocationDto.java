package com.anglernation.dtos;

import com.anglernation.models.locations.LocationVisibility;
import java.util.UUID;

public record LocationDto(
        UUID id,
        String name,
        LocationVisibility visibility,
        Double altitude,
        Double longitude,
        Double latitude
) {
}