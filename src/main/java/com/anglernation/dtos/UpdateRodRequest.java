package com.anglernation.dtos;

import com.anglernation.models.rods.RodType;
import com.anglernation.models.rods.RodPower;
import com.anglernation.models.rods.RodAction;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateRodRequest(
        RodType rodType,
        String name,
        @Positive Double length,
        @PositiveOrZero Double weight,
        RodPower rodPower,
        RodAction rodAction
) {
}