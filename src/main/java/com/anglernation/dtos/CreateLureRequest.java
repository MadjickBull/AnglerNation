package com.anglernation.dtos;

import com.anglernation.models.lures.LureType;
import com.anglernation.models.lures.LureBuoyancy;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateLureRequest(
        @NotNull LureType lureType,
        @NotBlank String name,
        @NotBlank String color,
        @Positive Double size,
        @PositiveOrZero Double weight,
        @NotNull LureBuoyancy lureBuoyancy
) {
}