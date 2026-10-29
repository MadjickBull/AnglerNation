package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;

@Schema(description = "Catch DTO with front-end payload")
public class CatchDto {

    @Schema(
            description = "Unique identifier for the catch",
            example = "123e4567-e89b-12d3-a456-426614174000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID id;

    @Schema(
            description = "Location ID where the fish was caught",
            example = "123e4567-e89b-12d3-a456-426614174000"
    )
    private UUID locationId;

    @Schema(
            description = "Rod ID used for the catch",
            example = "123e4567-e89b-12d3-a456-426614174000"
    )
    private UUID rodId;

    @Schema(
            description = "Lure ID used for the catch",
            example = "123e4567-e89b-12d3-a456-426614174000"
    )
    private UUID lureId;

    @Schema(
            description = "Line ID used for the catch",
            example = "123e4567-e89b-12d3-a456-426614174000"
    )
    private UUID lineId;

    @Schema(
            description = "Fish ID that was caught",
            example = "123e4567-e89b-12d3-a456-426614174000"
    )
    private UUID fishId;

    @Schema(
            description = "Angler ID who made the catch",
            example = "123e4567-e89b-12d3-a456-426614174000"
    )
    private UUID anglerId;

    @Schema(
            description = "Date/time when the catch was recorded",
            example = "2024-01-15T10:30:00Z"
    )
    private Instant createdAt;

    public CatchDto() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getLocationId() {
        return locationId;
    }

    public void setLocationId(UUID locationId) {
        this.locationId = locationId;
    }

    public UUID getRodId() {
        return rodId;
    }

    public void setRodId(UUID rodId) {
        this.rodId = rodId;
    }

    public UUID getLureId() {
        return lureId;
    }

    public void setLureId(UUID lureId) {
        this.lureId = lureId;
    }

    public UUID getLineId() {
        return lineId;
    }

    public void setLineId(UUID lineId) {
        this.lineId = lineId;
    }

    public UUID getFishId() {
        return fishId;
    }

    public void setFishId(UUID fishId) {
        this.fishId = fishId;
    }

    public UUID getAnglerId() {
        return anglerId;
    }

    public void setAnglerId(UUID anglerId) {
        this.anglerId = anglerId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}