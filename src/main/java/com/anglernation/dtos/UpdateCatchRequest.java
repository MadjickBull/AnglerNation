package com.anglernation.dtos;

import java.util.UUID;

public record UpdateCatchRequest(
        UUID locationId,
        UUID rodId,
        UUID lureId,
        UUID lineId,
        UUID fishId
) {
}