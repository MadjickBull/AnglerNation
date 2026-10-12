package com.anglernation.dtos;

import com.anglernation.models.lures.LureType;
import com.anglernation.models.lures.LureBuoyancy;
import java.util.UUID;

public record LureDto(
        UUID id,
        LureType lureType,
        String name,
        String color,
        Double size,
        Double weight,
        LureBuoyancy lureBuoyancy,
        UUID ownerId
) {
}