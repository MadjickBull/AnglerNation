package com.anglernation.dtos;

import com.anglernation.models.lines.LineType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateLineRequest(
        @NotNull LineType lineType,
        @NotBlank String name,
        @Positive Double diameter,
        @Positive Integer breakingStrength,
        @NotBlank String color
) {
}