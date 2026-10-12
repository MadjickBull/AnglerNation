package com.anglernation.dtos;

import java.time.Instant;
import java.util.UUID;

public record PostDto(
        UUID id,
        String title,
        String description,
        UUID authorId,
        Instant createdAt,
        Instant updatedAt
) {
}