package com.anglernation.dtos;

import com.anglernation.models.Fish.FishType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateFishRequest(
        @NotNull FishType fishType,
        @NotBlank String name,
        @PositiveOrZero Double weight,
        @PositiveOrZero Double size
) {
}