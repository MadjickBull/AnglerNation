package com.anglernation.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;
public record CreateCatchRequest(
        @NotNull UUID locationId,
        UUID rodId,
        UUID lureId,
        UUID lineId,
        @NotNull UUID fishId
) {
}