package com.anglernation.dtos;

import com.anglernation.models.lines.LineType;
import java.util.UUID;

public record LineDto(
        UUID id,
        LineType lineType,
        String name,
        Double diameter,
        Integer breakingStrength,
        String color,
        UUID ownerId
) {
}