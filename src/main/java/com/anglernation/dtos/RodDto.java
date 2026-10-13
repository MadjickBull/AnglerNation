package com.anglernation.dtos;

import com.anglernation.models.rods.RodType;
import com.anglernation.models.rods.RodPower;
import com.anglernation.models.rods.RodAction;
import java.util.UUID;

public record RodDto(
        UUID id,
        RodType rodType,
        String name,
        Double length,
        Double weight,
        RodPower rodPower,
        RodAction rodAction,
        UUID ownerId
) {
}