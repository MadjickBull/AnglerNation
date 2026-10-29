package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import com.anglernation.models.rods.RodType;
import com.anglernation.models.rods.RodPower;
import com.anglernation.models.rods.RodAction;
import java.util.UUID;

@Schema(description = "Rod DTO with front-end payload")
public class RodDto {

    @Schema(
            description = "Unique identifier for the rod",
            example = "123e4567-e89b-12d3-a456-426614174000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID id;

    @Schema(
            description = "Type of rod (SPIN, CAST, TROLL, etc.)",
            example = "SPIN"
    )
    private RodType rodType;

    @Schema(
            description = "Name/model of the rod",
            example = "Fast Action Casting Rod",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @Schema(
            description = "Length of the rod in feet",
            example = "7.5"
    )
    private Double length;

    @Schema(
            description = "Weight of the rod in ounces",
            example = "2.3"
    )
    private Double weight;

    @Schema(
            description = "Power rating of the rod (LIGHT, MEDIUM, HEAVY)",
            example = "MEDIUM"
    )
    private RodPower rodPower;

    @Schema(
            description = "Action type of the rod (FAST, MODERATE, SLOW)",
            example = "FAST"
    )
    private RodAction rodAction;

    @Schema(
            description = "Owner user ID",
            example = "123e4567-e89b-12d3-a456-426614174000"
    )
    private UUID ownerId;

    public RodDto() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public RodType getRodType() {
        return rodType;
    }

    public void setRodType(RodType rodType) {
        this.rodType = rodType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public RodPower getRodPower() {
        return rodPower;
    }

    public void setRodPower(RodPower rodPower) {
        this.rodPower = rodPower;
    }

    public RodAction getRodAction() {
        return rodAction;
    }

    public void setRodAction(RodAction rodAction) {
        this.rodAction = rodAction;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }
}