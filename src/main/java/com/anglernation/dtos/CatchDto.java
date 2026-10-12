package com.anglernation.dtos;

import java.time.Instant;
import java.util.UUID;

public record CatchDto(
        UUID id,
        UUID locationId,
        UUID rodId,
        UUID lureId,
        UUID lineId,
        UUID fishId,
        UUID anglerId,
        Instant createdAt
) {
}