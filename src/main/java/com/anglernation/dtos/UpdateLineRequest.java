package com.anglernation.dtos;

import com.anglernation.models.lines.LineType;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateLineRequest(
        LineType lineType,
        String name,
        @Positive Double diameter,
        @Positive Integer breakingStrength,
        String color
) {
}