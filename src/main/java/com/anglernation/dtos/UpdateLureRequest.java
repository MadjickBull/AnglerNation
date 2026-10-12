package com.anglernation.dtos;

import com.anglernation.models.lures.LureType;
import com.anglernation.models.lures.LureBuoyancy;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateLureRequest(
        LureType lureType,
        String name,
        String color,
        @Positive Double size,
        @PositiveOrZero Double weight,
        LureBuoyancy lureBuoyancy
) {
}