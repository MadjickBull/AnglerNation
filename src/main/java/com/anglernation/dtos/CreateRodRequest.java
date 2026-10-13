package com.anglernation.dtos;

import com.anglernation.models.rods.RodType;
import com.anglernation.models.rods.RodPower;
import com.anglernation.models.rods.RodAction;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateRodRequest(
        @NotNull RodType rodType,
        @NotBlank String name,
        @Positive Double length,
        @PositiveOrZero Double weight,
        @NotNull RodPower rodPower,
        @NotNull RodAction rodAction
) {
}