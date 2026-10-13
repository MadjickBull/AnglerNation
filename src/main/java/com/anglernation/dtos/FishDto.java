package com.anglernation.dtos;

import com.anglernation.models.Fish.FishType;
import java.util.UUID;

public record FishDto(
        UUID id,
        FishType fishType,
        String name,
        Double weight,
        Double size
) {
}