package com.anglernation.dtos;

import com.anglernation.models.Fish.FishType;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateFishRequest(
        FishType fishType,
        String name,
        @PositiveOrZero Double weight,
        @PositiveOrZero Double size
) {
}